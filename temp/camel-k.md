input


Kafka Not Secured Source を選ぶ
# topic name
user1.earth.dbo.Orders

# Brokers
earth-cluster-kafka-bootstrap.demo-pj.svc:9092

atlasmap
# Uri
atlasmap:file:/etc/camel/resources/order-mapping.adm

Kafka Not Secured Sink を選ぶ
# topic name
incoming-topic

# Brokers
earth-cluster-kafka-bootstrap.demo-pj.svc:9092



kamel run ./openshift/camel-k/order.yaml --resource file:./openshift/camel-k/order-mapping.adm -n demo-pj
