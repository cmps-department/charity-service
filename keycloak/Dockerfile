FROM quay.io/keycloak/keycloak:20.0.5

LABEL org.opencontainers.image.source=https://github.com/cmps-department/charity-service

ADD realm-export.json /opt/keycloak/data/import/realm-export.json

ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]
CMD ["start-dev", "--import-realm"]
