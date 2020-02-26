# Kraken Search
Spring Boot를 이용한 openAPI 검색 서버 프로젝트

# Feature
- 회원 가입/로그인
    - JWT Token 인증 방식
- 책 검색
    - Kakao OpenApi, Naver Open API 사용
    - 기본적으로 Kakao OpenApi를 이용하여 검색하나, Kakao OpenAPI 장애 발생 시, Naver OpenAPI를 사용하여 검색한다.
- 내 검색 히스토리 조회
    - 사용자가 검색한 이력을 최근 날짜순으로 제공합니다.
- 인기 키워드 목록 조회
    - Redis를 이용하여 키워드별 검색 키워드를 실시간 저장 및 조회 제공.
    - Redis 장애 발생 시, 복구 배치 처리 작업 구현이 추가로 필요 함. 

# Using Open Source
- spring-boot-starter-security
    - Spring 기반 어플리케이션 인증 보안 기능 제공
- spring-boot-starter-data-jpa
    - Spring에서 JPA를 추상화 하여 사용할수 있도록 제공하는 모듈
    - 추상화된 Repository 인터페이스 기반으로 사용한다.
- spring-boot-starter-cache
    - 반복적인 데이터에 대한 캐싱 처리 용도로
    Spring에서 다른 캐싱 솔루션들을 쉽게 연동하여 사용할수 있도록 추상화하여 제공하는 Library 
    (Couchbase, Hazelcast, Redis, Ehcache 등을 지원)
- spring-retry
    - 어플리케이션에서 작업 실패 시 재시도 및 복구 처리 등을 할수있도록 제공
    - 어노테이션 또는 RetryTemplate을 사용하여 구현 가능 
- jjwt
    - JWT 토큰 생성 및 검증을 위한 Library
- lombok
    - 어노테이션 기반로 Class에 반복적인 기능을 깔끔하고 쉽게 도와주는 Library
    - Getter, Setter, Construct, Builder 등을 어노테이션으로 쉽게 구현할수 있음
    - 컴파일 시점에 AnnotationProcessor를 이용하여 실제 코드를 생성한다.
    - 사용 개발 툴에서 별도의 플러그인을 설치해야함.
- embedded-redis
    - Local 환경에서 별도의 Redis 구축 없이 사용할수 있도록 H2 DB와 비슷한 내장형 Redis Library 

## API Test
Intellij IDEA 를 사용할 경우, http 폴더 안에 `api-test-request.http`를 이용하여 확인하실수 있습니다. 


### 회원 가입
계정 회원 가입시 사용합니다.

#### API Path
POST /v1/account/signup

#### Request Body
|     키    |  타입  |     설명    | 필수여부 | 기본값 |
|:---------:|:------:|:-----------:|:--------:|:------:|
|   userId  | String |  사용자 ID  |   true   |        |
|  password | String |   비밀번호  |   true   |        |
|  lastName | String |     이름    |   false  |        |
| firstName | String |      성     |   false  |        |
|   email   | String | 이메일 주소 |   false  |        |

#### Response
204 No Content

### 회원 로그인
생성된 회원 계정으로 로그인시 사용합니다.
로그인 요청 성공 시 JWT 토큰을 반환합니다.

#### API Path
POST /v1/account/signin

#### Request Body
|    키    |  타입  |       설명      | 필수여부 | 기본값 |
|:--------:|:------:|:---------------:|:--------:|:------:|
|  userId  | String |    사용자 ID    |   true   |        |
| password | String | 사용자 비밀번호 |   true   |        |

#### Response
|   키  | 타입   | 설명          | 필수여부 | 기본값 |
|:-----:|--------|---------------|:--------:|:------:|
| token | String | JWT 인증 토큰 |   true   |        |

- response example
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOiJVU0VSIiwidXNlcklkIjoiZGtzd2pkYWxzODkiLCJpYXQiOjE1ODI2MzQ0NjcsImV4cCI6MTU4MjYzODA2N30.cnsxBQZkwQ7Dx2LoHeBc7OBO9jdMrE7dmbLYx1Ko8I0"
}
```

### 책 검색
카카오 open API를 이용하여 책을 검색하여 반환합니다.
(카카오 open API 장애 발생 시 Naver OpenAPI를 이용하여 반환합니다.)

#### API Path
GET /v1/search/book

#### Request Header
|       키      |  타입  |             설명             | 필수여부 | 기본값 |
|:-------------:|:------:|:----------------------------:|:--------:|:------:|
| Authorization | String | 인증 토큰 Bearer {jwt-token} |   true   |        |

#### Request Parameter
|    키   |  타입  |        설명        | 필수여부 | 기본값 |
|:-------:|:------:|:------------------:|:--------:|:------:|
| keyword | String |     검색 키워드    |   true   |        |
|   page  | Number | 검색할 페이지 번호 |   false  |    1   |
|   size  | Number |   조회 아이템 수   |   false  |   10   |

#### Response
|         키        |      타입     |          설명         | 필수여부 | 기본값 |
|:-----------------:|:-------------:|:---------------------:|:--------:|:------:|
|       items       |     Array     |   응답 데이터 리스트  |   true   |   []   |
|    items.title    |     String    |       도서 제목       |          |        |
|   items.contents  |     String    |       도서 소개       |          |        |
|     items.url     |     String    |       도서 상세       |          |        |
|     items.isbn    |     String    |  국제 표준 도서 번호  |          |        |
|   items.datetime  |     String    |     도서 출판 날짜    |          |        |
|   items.authors   | Array<String> |    도서 저자 리스트   |          |        |
|  items.publisher  |     String    |      도서 출판사      |          |        |
| items.translators | Array<String> |   도서 번역자 리스트  |          |        |
|    items.price    |     Number    |       도서 정가       |          |        |
|  items.salePrice  |     Number    |      도서 판매가      |          |        |
|  items.thumbnail  |     String    |  도서 표지 썸네일 URL |          |        |
|        page       |     Number    |    현재 페이지 번호   |          |        |
|       count       |     Number    | 현재 페이지 아이템 수 |          |        |
|        size       |     Number    | 요청 페이지 아이템 수 |          |        |
|     totalPage     |     Number    |      총 페이지 수     |          |        |
|     totalCount    |     Number    |      총 아이템 수     |          |        |

- response example
```json
{
    "items": [
        {
            "title": "오늘의 말씀 365(스프링)",
            "content": "매일 아침 갓피플 180만 회원들의 하루를 열어준 ‘오늘의 말씀’ 카드를 365 묵상캘린더로 엮었습니다.  아름다운 자연 풍경 위에 은혜로운 매일의 말씀을 담았습니다. 어디에 있든지 주님의 크고 아름다우심을 묵상하며 주님과 동행하세요!  영어성경(NIV) 구절을 함께 수록하여 더욱 깊은 이해를 돕습니다. 전도와 묵상 나눔을 위한 선물용으로도 좋습니다.",
            "url": "https://search.daum.net/search?w=bookpage&bookId=841893&q=%EC%98%A4%EB%8A%98%EC%9D%98+%EB%A7%90%EC%94%80+365%28%EC%8A%A4%ED%94%84%EB%A7%81%29",
            "isbn": "8960975141 9788960975149",
            "datetime": "2017-11-20",
            "authors": [
                "편집부"
            ],
            "publisher": "규장",
            "translators": [],
            "price": 9000,
            "salePrice": 8100,
            "thumbnail": "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F841893%3Ftimestamp%3D20200224120322",
            "status": null
        }
    ],
    "page": 1,
    "count": 1,
    "size": 1,
    "totalPage": 4263,
    "totalCount": 4263
}
```

### 검색 히스토리 조회
사용자가 검색한 히스토리 내역을 반환합니다.

#### API Path
GET /v1/history/search

#### Request Header
|       키      |  타입  |             설명             | 필수여부 | 기본값 |
|:-------------:|:------:|:----------------------------:|:--------:|:------:|
| Authorization | String | 인증 토큰 Bearer {jwt-token} |   true   |        |

#### Request Parameter
|    키   |  타입  |        설명        | 필수여부 | 기본값 |
|:-------:|:------:|:------------------:|:--------:|:------:|
|   page  | Number | 검색할 페이지 번호 |   false  |    1   |
|   size  | Number |   조회 아이템 수   |   false  |   10   |

#### Response
|             키             |  타입  |                    설명                   | 필수여부 | 기본값 |
|:--------------------------:|:------:|:-----------------------------------------:|:--------:|:------:|
|            items           |  Array |             응답 데이터 리스트            |   true   |   []   |
|        items.account       | Object |                 계정 정보                 |          |        |
|      items.account.id      | Number |                 계정 Pk ID                |          |        |
|    items.account.userId    | String |               계정 로그인 ID              |          |        |
|     items.account.role     | String | 계정 권한 USER: 일반 사용자 ADMIN: 관리자 |          |        |
|  items.account.createdDate | String |                계정 생성일                |          |        |
| items.account.modifiedDate | String |              계정 정보 수정일             |          |        |
|      items.searchType      | String |                 검색 타입                 |          |        |
|     items.searchKeyword    | String |                검색 키워드                |          |        |
|      items.createdDate     | String |                   생성일                  |          |        |
|     items.modifiedDate     | String |                   수정일                  |          |        |
|            page            | Number |              현재 페이지 번호             |          |        |
|            count           | Number |           현재 페이지 아이템 수           |          |        |
|            size            | Number |           요청 페이지 아이템 수           |          |        |
|          totalPage         | Number |                총 페이지 수               |          |        |
|         totalCount         | Number |                총 아이템 수               |          |        |

- response example
```json
{
    "items": [
        {
            "id": 1,
            "account": {
                "id": 1,
                "userId": "dkswjdals89",
                "role": "USER",
                "createdDate": "2020-02-26 01:04:17",
                "modifiedDate": "2020-02-26 01:04:17"
            },
            "searchType": "BOOK",
            "searchKeyword": "스프링",
            "createdDate": "2020-02-26 01:04:40",
            "modifiedDate": "2020-02-26 01:04:40"
        }
    ],
    "page": 1,
    "count": 1,
    "size": 10,
    "totalPage": 1,
    "totalCount": 1
}
```


### 검색 키워드 TOP 10
사용자들이 검색한 인기 키워드 TOP 10 리스트를 반환합니다.

#### API Path
GET /v1/statistics/popularKeyword

#### Request Header
|       키      |  타입  |             설명             | 필수여부 | 기본값 |
|:-------------:|:------:|:----------------------------:|:--------:|:------:|
| Authorization | String | 인증 토큰 Bearer {jwt-token} |   true   |        |

#### Response
|       키      |  타입  |          설명         | 필수여부 | 기본값 |
|:-------------:|:------:|:---------------------:|:--------:|:------:|
|     items     |  Array |   응답 데이터 리스트  |   true   |   []   |
| items.keyword | String |         키워드        |          |        |
|  items.count  | String |        조회 수        |          |        |
|      page     | Number |    현재 페이지 번호   |          |        |
|     count     | Number | 현재 페이지 아이템 수 |          |        |
|      size     | Number | 요청 페이지 아이템 수 |          |        |
|   totalPage   | Number |      총 페이지 수     |          |        |
|   totalCount  | Number |      총 아이템 수     |          |        |

- response example
```json
{
    "items": [
        {
            "keyword": "코틀린",
            "count": 10
        },
        {
            "keyword": "effective java",
            "count": 8
        },
        {
            "keyword": "토비",
            "count": 5
        },
        {
            "keyword": "디자인 패턴",
            "count": 4
        },
        {
            "keyword": "메이븐",
            "count": 3
        },
        {
            "keyword": "node.js",
            "count": 3
        },
        {
            "keyword": "어린왕자",
            "count": 2
        },
        {
            "keyword": "그래들",
            "count": 2
        },
        {
            "keyword": "aws",
            "count": 2
        },
        {
            "keyword": "스프링",
            "count": 1
        }
    ],
    "count": 10
}
```
