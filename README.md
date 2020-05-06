# telegram-music-bot

Собери проект "mvn package";
В папке \target\bin должны появиться скрипты
В pom.xml у тебя указан workerBot, поэтому в Procfile пиши: "worker: sh target/bin/workerBot";
Тип процесса "worker" в Procfile указывается если бот использует Long Polling, если Webhook, то запись другая.
Дальше по инструкции на сайте heroku:
heroku login
heroku git:clone -a fathomless-anchorage-20645
cd fathomless-anchorage-20645
git add .
git commit -am "make it better"
git push heroku master
heroku ps:scale worker=1

