from locust import FastHttpUser, task, between, TaskSet
from locust import events
from locust.runners import MasterRunner, WorkerRunner
from json import JSONDecodeError
import random


class MyTask(TaskSet):

    @task
    def post_comments(self):
        request_body = {
            "productId": 43,
            "memberId": random.randint(1, 10000000),
            "quantity": 1,
            "address": "서울 특별시"
        }

        with self.client.post("/v2/orders", json=request_body, catch_response=True):
            pass

        # print(response.status_code) -> 200
        # print(response.text) -> {"statusMessage":"성공","data":null} -> 위처럼 json()으로 바꾸는 방식으로 사용해야함


class LocustUser(FastHttpUser):
    host = "http://localhost:8086"
    tasks = [ MyTask ]
    min_wait = 5000
    max_wait = 15000