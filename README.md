# 🛒 JPA Shop (Study)
> <b>[First Spring](https://github.com/seohyun27/firstspring.git)</b>에서 학습한 내용을 바탕으로 진행한 **JPA 심화 및 REST API 설계 실습 프로젝트**입니다.<br/>
> 인프런 강의를 통해 엔티티 설계부터 API 개발까지의 과정을 학습했습니다.

## 🛠 Tech Stack
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat&logo=springboot&logoColor=white) 
![JPA](https://img.shields.io/badge/JPA-59666C?style=flat&logo=hibernate&logoColor=white) 
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=flat&logo=gradle&logoColor=white)

## 📂 Contents

### 📝 Study Notes
학습 과정에서 중요하게 다룬 개념을 정리한 노트입니다.
- **📘 domain-db.md**
  - 스프링 부트 환경에서의 **엔티티 설계** 및 데이터베이스 설정 방법
- **📘 repository-service-controller.md**
  - 스프링의 **계층형 아키텍처(Layered Architecture)** 심화 학습
  - Repository, Service, Controller의 역할 및 상호작용 정리

### 🧾 Program Code
실습을 통해 작성한 예제 코드입니다. (`jpashop/` 디렉토리)
- **🛠️ Build Configuration**
  - `build.gradle`, `settings.gradle` : 프로젝트 빌드 및 의존성 설정
- **📁 src/main**
  - 도메인 모델, 비즈니스 로직 및 **REST API** 구현 코드
- **📁 src/test**
  - 주요 기능 및 API 동작 검증을 위한 테스트 코드
