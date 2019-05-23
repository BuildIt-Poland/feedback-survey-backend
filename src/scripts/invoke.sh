#!/bin/bash
#Source: https://github.com/riboseinc/aws-authenticating-secgroup-scripts

set -o errexit
set -o pipefail
set -o nounset

function sha256Hash() {
    printf "$1" | openssl dgst -sha256 -binary -hex | sed 's/^.* //'
}

function log() {
    local message="$1"
    local details="$2"

    printf "${message}\n" >&2
    printf "${details}\n\n" | sed 's/^/    /' >&2
}

function to_hex() {
    printf "$1" | od -A n -t x1 | tr -d [:space:]
}

function hmac_sha256() {
    printf "$2" | \
        openssl dgst -binary -hex -sha256 -mac HMAC -macopt hexkey:"$1" | \
        sed 's/^.* //'
}

## http://docs.aws.amazon.com/general/latest/gr/sigv4-create-canonical-request.html
function task_1() {
    local http_request_method="$1"
    local canonical_uri="/${api_uri}"
    local canonical_query=""

    local header_host="host:${api_host}"
    local body="$2"
    local hashedBody="x-amz-content-sha256:$(sha256Hash ${body})"
    local canonical_headers="${header_host}\n${hashedBody}\n${header_x_amz_date}"

    local request_payload=$(sha256Hash "$body")
    local canonical_request="${http_request_method}\n${canonical_uri}\n${canonical_query}\n${canonical_headers}\n\n${signed_headers}\n${request_payload}"

    #log "canonical_request=" "${canonical_request}"
    printf "$(sha256Hash ${canonical_request})"
}

## http://docs.aws.amazon.com/general/latest/gr/sigv4-create-string-to-sign.html
function task_2() {
    local hashed_canonical_request="$1"
    local sts="${algorithm}\n${timestamp}\n${credential_scope}\n${hashed_canonical_request}"
    #log "string_to_sign=" "${sts}"
    printf "${sts}"
}

## http://docs.aws.amazon.com/general/latest/gr/sigv4-calculate-signature.html
function task_3() {
    local secret=$(to_hex "AWS4${aws_secret_key}")
    local k_date=$(hmac_sha256 "${secret}" "${today}")
    local k_region=$(hmac_sha256 "${k_date}" "${aws_region}")
    local k_service=$(hmac_sha256 "${k_region}" "${aws_service}")
    local k_signing=$(hmac_sha256 "${k_service}" "aws4_request")
    local string_to_sign="$1"

    local signature=$(hmac_sha256 "${k_signing}" "${string_to_sign}" | sed 's/^.* //')
    #log "signature=" "${signature}"
    printf "${signature}"
}

## http://docs.aws.amazon.com/general/latest/gr/sigv4-add-signature-to-request.html#sigv4-add-signature-auth-header
function task_4() {
    local credential="Credential=${aws_access_key}/${credential_scope}"
    local s_headers="SignedHeaders=${signed_headers}"
    local signature="Signature=$1"

    local authorization_header="Authorization: ${algorithm} ${credential}, ${s_headers}, ${signature}"
    #log "authorization_header=" "${authorization_header}"
    printf "${authorization_header}"
}

function sign_it() {
    local method="$1"
    local body="$2"
    local hashed_canonical_request=$(task_1 "${method}" "${body}")
    local string_to_sign=$(task_2 "${hashed_canonical_request}")
    local signature=$(task_3 "${string_to_sign}")
    local authorization_header=$(task_4 "${signature}")
    printf "${authorization_header}"
}

function invoke_it() {
    local http_method="$1"
    local body="$2"
    local bodyHash="x-amz-content-sha256:$(sha256Hash ${body})"
    local authorization_header=$(sign_it "${http_method}" "${body}")
    printf "> ${http_method}-ing ${api_url}\n"

    curl -si -X ${http_method} "${api_url}" -H "${authorization_header}" -H "${header_x_amz_date}" -H "${bodyHash}" \
    -H 'Content-Type: text/plain' -d "${body}"
}

function main() {
    local method="$1"
    local credentials="$2"
    local url="$3"
    local body="$4"
    local aws_region="$5"
    local aws_service="execute-api"

    local aws_access_key=$(cut -d':' -f1 <<<"${credentials}")
    local aws_secret_key=$(cut -d':' -f2 <<<"${credentials}")
    local api_url="${url}"

    local timestamp=${timestamp-$(date -u +"%Y%m%dT%H%M%SZ")} #$(date -u +"%Y%m%dT%H%M%SZ") #"20171226T112335Z"
    local today=${today-$(date -u +"%Y%m%d")}  # $(date -u +"%Y%m%d") #20171226

    local api_host=$(printf ${api_url} | awk -F/ '{print $3}')
    local api_uri=$(printf ${api_url} | grep / | cut -d/ -f4-)

    local algorithm="AWS4-HMAC-SHA256"
    local credential_scope="${today}/${aws_region}/${aws_service}/aws4_request"

    local signed_headers="host;x-amz-content-sha256;x-amz-date"
    local header_x_amz_date="x-amz-date:${timestamp}"

    invoke_it "${method}" "${body}"

    echo -e "\n\nDONE!!!"
}

main "$@"