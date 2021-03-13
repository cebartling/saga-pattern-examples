#!/bin/bash
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" -d "$POSTGRES_DB"  <<-EOSQL
  CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
  CREATE EXTENSION IF NOT EXISTS "pgcrypto";
  create role $ANON nologin;
  create role $AUTHENTICATOR noinherit login password '$POSTGRES_PASSWORD';
  grant $ANON to $AUTHENTICATOR;
EOSQL
