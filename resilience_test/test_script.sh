artillery run --output case1/case1_report.json resilience-test-case1.yaml
sleep 2m
artillery report case1/case1_report.json --output case1/case1_report.html

artillery run --output case2/case2_report.json resilience-test-case2.yaml
sleep 2m
artillery report case2/case2_report.json --output case2/case2_report.html

artillery run --output case3/case3_report.json resilience-test-case3.yaml
sleep 2m
artillery report case3/case3_report.json --output case3/case3_report.html