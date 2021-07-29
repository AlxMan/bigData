
 Fliebeat安装
2.1 下载安装Filebeat
# 1.下载
wget https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-7.3.0-linux-x86_64.tar.gz
# 2.解压
tar -zxvf filebeat-7.3.0-linux-x86_64.tar.gz 
# 3.安装
mv filebeat-7.3.0-linux-x86_64.tar.gz  filebeat-7.3.0

2.2 修改配置文件
filebeat.inputs:
- type: log
  paths:
    - /opt/lagou/data/logs/lagou_orders.json
output.kafka:
  hosts: ["linux121:9092"]
  topic: "lagou_order"
  
2.3 采集日志数据发送到kafka
启动zookeeper,kafka

创建topic
kafka-topics.sh --create --topic lagou_order --zookeeper linux121:2181/kafka --partitions 1 --replication-factor 1
启动filebeat

./filebeat -e -c filebeat.yml
3. logstash读取Kafka
1. logstash配置
# vim /opt/lagou/servers/Logstash-6.2.0/config/kafka-es.conf
input {
  kafka {
    bootstrap_servers => "linux121:9092"
    topics => ["lagou_order"]
    codec => "json"
  }
}

output {
  elasticsearch {
    hosts => ["http://centos01:9200","http://linux122:9200","http://centos03:9200"]
    index => "logstash-kafka-es-%{+YYYY.MM.dd}"
  }
  stdout{codec => rubydebug }
}
2. 启动logstash
bin/logstash -f config/kafka-es.conf
3. 上传数据
/opt/lagou/data/logs/lagou_orders.json








