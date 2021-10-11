#!/bin/bash

set -e
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

: ${NEO4J_CONTAINER_ROOT:?"Environment variable NEO4J_CONTAINER_ROOT is unset or empty"}
: ${NEO4J_DATA_DIR:?"Environment variable NEO4J_DATA_DIR is unset or empty"}
: ${NEO4J_CONVERTED_CSV_DIR:?"Environment variable NEO4J_CONVERTED_CSV_DIR is unset or empty"}
: ${NEO4J_CSV_POSTFIX:?"Environment variable NEO4J_CSV_POSTFIX is unset or empty"}
: ${NEO4J_VERSION:?"Environment variable NEO4J_VERSION is unset or empty"}

# make sure directories exist
mkdir -p ${NEO4J_CONTAINER_ROOT}/{logs,import,plugins}

# start with a fresh data dir (required by the CSV importer)
mkdir -p ${NEO4J_DATA_DIR}
rm -rf ${NEO4J_DATA_DIR}/*

docker run --rm \
    --user="$(id -u):$(id -g)" \
    --publish=7474:7474 \
    --publish=7687:7687 \
    --volume=${NEO4J_DATA_DIR}:/data \
    --volume=${NEO4J_CONVERTED_CSV_DIR}:/import \
    --volume=${NEO4J_CONTAINER_ROOT}/logs:/logs \
    --volume=${NEO4J_CONTAINER_ROOT}/import:/var/lib/neo4j/import \
    --volume=${NEO4J_CONTAINER_ROOT}/plugins:/plugins \
    ${NEO4J_ENV_VARS} \
    neo4j:${NEO4J_VERSION} \
    neo4j-admin import \
    --id-type=INTEGER \
    --nodes=Person="/import/dynamic/person${NEO4J_CSV_POSTFIX}" \
    --relationships=KNOWS="/import/dynamic/person_knows_person${NEO4J_CSV_POSTFIX}" \
    --delimiter '|'
