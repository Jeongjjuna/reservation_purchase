# http_request_tool.py

import time
import requests
import random
import threading
from concurrent.futures import ThreadPoolExecutor

# 한명이 수행하는 시나리오를 순서대로 작성한다.(결제 프로세스)
def sinario(user_id):

    # 예약 상품 남은 수량 조회 (완료) -> GET 예약상품 재고 수 조회 v1/reservation-products/{reservation_id}/stock
    product_id = "43"
    url = "http://localhost:8083/v1/stocks/products/" + product_id
    try:
        response = requests.get(url)
        print(f"재고 수량 - {response.json()['data']['stockCount']}", end=" ")
        print(f"사용자 : {user_id}")
        # print(f"Request to {url} completed with status code {response.json()['data']['stockCount']}")
    except Exception as e:
        print(f"Error sending request to {url}: {e}")
        return

    # 결제화면 들어가기 버튼 클릭 (완료) ->  POST 주문 생성 요청 : v1/orders
    url = "http://localhost:8083/v1/orders"
    response = None
    try:
        body = {
            "productId" : 43,
            "productType" : "reservationProduct",
            "quantity" : 1,
            "memberId" : user_id,
            "address" : "서울"
        }
        response = requests.post(url, json=body)
        print(f"주문(결제화면들어가기) {response.json()['desc']}, 누가? {user_id}")
        # print(f"Request to {url} completed with status code {response.json()['desc']}")
    except Exception as e:
        # print("error")
        print(f"Error sending request to {url}: {e}")
        return


    # # 20%는 고객 변심 이탈 (완료) -> DELETE 주문 취소 요청 : v1/orders
    random_number = random.randint(0, 99)
    order_id = response.json()['data']
    if random_number < 20:
        url = "http://localhost:8083/v1/orders/" + str(order_id)
        try:
            response = requests.delete(url)
            print(f"고객 변심 이탈 : {response.json()['desc']}")
            # print(f"Request to {url} completed with status code {response.json()['desc']}")
        except Exception as e:
            print("error")
            # print(f"Error sending request to {url}: {e}")

        print("고객 변심 이탈 재고 + 1")
        return


    # 결제 버튼 클릭(결제 요청 API) (완료) -> POST 결제하기 요청 : v1/payments
    url = "http://localhost:8083/v1/payments"
    try:
        body = {
            "orderId" : order_id,
            "memberId" : user_id
        }
        response = requests.post(url, json=body)
        print(f"결제 {response.json()['desc']}")
        # print(f"Request to {url} completed with status code {response.json()['desc']}")
    except Exception as e:
        print("error")
        # print(f"Error sending request to {url}: {e}")
        return



def main():
    # 1만명의 사람들이 결제 시나오리를 수행한다.
    num_requests = 10000

    with ThreadPoolExecutor(max_workers=100) as executor:
        tasks = [executor.submit(sinario, user_id) for user_id in range(1, num_requests + 1)]

        for future in tasks:
            future.result()

if __name__ == "__main__":

    start_time = time.time()
    main()
    end_time = time.time()

    execution_time = end_time - start_time
    print("코드 실행 시간:", execution_time, "초")
