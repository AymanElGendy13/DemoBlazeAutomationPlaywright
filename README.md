# DemoBlaze Automation Framework Using Playwright
<a href="https://playwright.dev"><img src="https://playwright.dev/img/playwright-logo.svg" width="50" alt="Playwright"/></a>
![Java](https://img.shields.io/badge/Java-23-red) ![Playwright](https://img.shields.io/badge/Playwright-1.50-blue) ![TestNG](https://img.shields.io/badge/TestNG-7.11-orange) ![Allure](https://img.shields.io/badge/Reporting-AllureReports-green)

End-to-end test automation framework for DemoBlaze e-commerce website implementing high-level business scenarios.

### Website
Link: [demoblaze Website](https://www.demoblaze.com/)

## 🚀 Key Features
-**Modern Playwright Framework with Java**.
- **End-to-End Test Coverage** of core user journeys.
- **Page Object Model** and Fluent Pattern Interface design.
- **JSON Data-Driven** testing approach.
- **Multi-Layer Reporting**:
  - Allure Reports with screenshots.
  - Log4j2 execution logs.
- **Advanced Features**:
  - Automatic waiting and retry mechanisms.
  - TestNG Listeners for custom reporting.
  - Smart element waits with fluent patterns.
  - Cross-browser support (Chrome/Firefox/Edge).
  - CI/CD ready (Jenkins/GitHub Actions compatible).

## 🛠️ Technologies Used

| Component          | Technology Stack |
|--------------------|------------------|
| Test Framework     | TestNG 7.11      |
| Language           | Java 23          |
| Web Automation     | Playwright 1.50       |
| Design Pattern     | POM + Fluent pattern  |
| Data Management    | JSON files       |
| Reporting & Logging         | Allure Reports + Log4j2 + Playwright HTML|
| Build Tool         | Maven            |
| Visual Validation  | Screenshot embedding |

## 📂 Project Structure
```
demoblaze-automation/
├── src/
│ ├── main/java/
│ │ ├── pages/ # Page Objects with fluent methods
│ │ ├── core.ui/ # Reusable UI components
│ │ ├── listeners/ # Custom TestNG listeners
│ │ ├── utils/ # Helper classes
│ │ └── DriverManager/ # Driver/page factories
| | └── resources/ # All properties files
│ │
│ └── test/java/
│ ├── testcases/ # TestNG test classes
│ ├── resources ├── testdata/ # JSON test data files
│ 
│
├── logs/ # Log4j2 output
├── allure-results/ # Allure raw reports
├── screenshots/ # Captures
└── pom.xml # Maven dependencies
└── testng.xml # configuration file for organizing and executing tests in TestNG
```

 ### Installation
```bash
- git clone https://github.com/your-repo/demoblaze-playwright.git
- cd demoblaze-playwright
-npm install

# Run tests
npx playwright test

# Generate Allure reports
npx allure serve allure-results
```
