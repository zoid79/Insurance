# 보험사 시스템

**보험사 시스템**은 보험 상품 관리 및 보험 관련 업무를 효과적으로 처리하기 위해 제작되었습니다. 본 프로젝트는 분산 프로그래밍1 과목에서 개발되었으며, 보험사 업무의 디지털화를 지원합니다.
___
## 기능

- **보험 상품 관리:** 다양한 보험 상품을 등록하고 관리합니다.
- **계약 관리:** 체결된 계약을 체계적으로 관리합니다.
- **사고 관리:** 고객이 신고한 사고를 검토, 관리합니다.
- **보상 관리:** 사고에 관한 보험금 청구를 효과적으로 관리합니다.
- **고객 관리:** 고객에 대한 정보와 그 고객이 가지고 있는 건물, 집의 정보를 관리합니다.
- **보상식 관리:** 보험에 가입된 건물의 용도, 건축 자제들의 위험도를 종합, 효과적으로 관리합니다.
- **직원 관리:** 직원의 직무와 계급에 따라 효과적으로 업무를 할 수 있는 UI를 만듭니다.
- **보험료 계산 및 정산:** 자동으로 보험료를 계산하고 정산합니다.
---
## 프로젝트 동기

- 2005년 당시 신 동아화제가 차세대 시스템 구축을 위해 제안한 RFP를 기반으로 요구사항을 분석, 설계하여 보험사 시스템을 설계하였다.
- 모든 코드는 JAVA로 이이루어져 있으며 일체의 프레임 워크를 사용하지 않았다.
## Service 구현
### 구현 방법
* ServiceIF에 먼저 함수를 정의하고 Service에 Override한 후 구현 (ServiceIF에 함수 정의할 때 throws RemoteException 해줄 것)
* Service에 필요한 ListImpl은 필드에 private final로 선언하고 생성자 파라미터로 받아서 주입
* Service를 구현하는데 필요한 ListImpl의 함수도 구현
* Service 함수 구현할 때 ListImpl로부터 가져온 데이터가 null인 경우 NoDataException, 가져온 리스트가 비어있는 경우 EmptyListException 던질 것 (TimeDelayException은 나중에)
## 구조
- 분산시스템을 활용하기 위해 rmi 통신을 사용하여 9개의 서버와 1개의 클라이언트, DB는 MySQL로 이루어져 있다.
- 서버는 CompensateServer, AccidentServer, PayServer, ContractServer, CustomerServer, EmployeeServer, InsuranceServer, CalculationFormulaServer, SaleServer로 구성되어 있다.
- DB의 테이블은 도메인 1개당 1개의 테이블로 설정하였다.
- 여러 server 사이의 통신을 위해 Service Container가 존재한다.
---
### 파트
* 김남훈
  * PayServiceIF & PaySerivce (요금을 납부하다)
  * ReportAccidentIF & ReportAccident (사고를 접수하다)
  * DB 설계 및 연결
* 성유진
  * CompensateServiceIF & CompensateService (보상을 결정하다)
  * MakePolicyServiceIF & MakePolicyService (보상지침을 수립하다)
* 이우성
  * ApplyInsuranceServiceIF & ApplayInsuranceService (보험가입을 신청하다)
  * UnderwriteServiceIF & UnderwriteService (인수심사를 하다)
  * Console UI 제작
* 최성훈
  * ConcludeServiceIF & ConcludeService (계약을 체결하다)
  * OfferServiceIF & OfferService (보험을 제안하다)
---
- 현재 해당 코드를 JAVA Spring, REACT 프레임워크를 사용하여 웹 개발로 refactoring 완료
