# workScheduler2
# In this moment this is not official readme.

mvn clean install -Dwildfly.username=*** -Dwildfly.password=***

GET
http://localhost:8080/workScheduler2/hello/Daniel
http://localhost:8080/workScheduler2/addInitial
http://localhost:8080/workScheduler2/workers
http://localhost:8080/workScheduler2/worker?name=Zbigniew&surname=Wisniewski
http://localhost:8080/workScheduler2/worker/Zbigniew.Wisniewski

POST
http://localhost:8080/workScheduler2/worker

PUT
http://localhost:8080/workScheduler2/worker

DELETE
http://localhost:8080/workScheduler2/worker?name=Zbigniew&surname=Wisniewski
http://localhost:8080/workScheduler2/worker/Zbigniew.Wisniewski