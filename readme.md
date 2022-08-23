# APPSERVICES-INTAGREATION-DEMO

Red Hat Integration （or RHAF）製品を利用したデモになります。
レガシーアプリケーションから、CDCを使ってアプリケーションモダナイズを促進するデモとなります。

# Installation
* プロジェクトをローカルに clone
* ターミナルから OpenShift に Login する
* `provision.sh` を実行する
* 作り直す場合には`delete.sh` を実行する
* Build時にはJDK11で実施する

# Note
ROSA 環境で実行する場合、Camel-Kの「order.yaml」を変更する必要あり。

# legacy application
https://quay.io/repository/nmushino/my-apache-php-app

# Author
* nmushino
* nmushino@redhat.com