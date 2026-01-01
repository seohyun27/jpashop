# 🛒 JPA Shop (Study)
> 인프런(Inflearn) 강의를 수강하며 진행한 **JPA 기반 쇼핑몰 예제 프로젝트**입니다.<br/>
> 학습 목적으로 작성된 코드와 실습 과정에서 정리한 핵심 개념 노트가 포함되어 있습니다.

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
  - 스프링의 **계층형 아키텍처(Layered Architecture)** 학습
  - Repository, Service, Controller의 역할 및 상호작용 정리

### 🧾 Program Code
실습을 통해 작성한 예제 코드입니다. (`jpashop/` 디렉토리)
- **🛠️ Build Configuration**
  - `build.gradle`, `settings.gradle` : 프로젝트 빌드 및 의존성 설정
- **📁 src/main**
  - 도메인 모델 및 비즈니스 로직이 구현된 메인 애플리케이션 코드
- **📁 src/test**
  - 주요 기능 검증을 위해 작성된 테스트 코드
