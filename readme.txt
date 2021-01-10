ZAP Docker User Guide: https://www.zaproxy.org/docs/docker/about/

Install instructions:
docker pull owasp/zap2docker-stable
docker run -u root -p 8090:8090 -i owasp/zap2docker-stable zap-x.sh -daemon -host 0.0.0.0 -port 8090 -config api.addrs.addr.name=.\* -config api.addrs.addr.regex=true -config api.disablekey=true -config scanner.attackOnStart=true -config view.mode=attack

ZAP homepage: http://localhost:8090/
ZAP report: http://localhost:8090/OTHER/core/other/htmlreport