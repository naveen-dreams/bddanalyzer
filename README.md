# BDD Framework Analyzer

A Java Spring Boot application that analyzes BDD (Behavior-Driven Development) frameworks and provides detailed metrics and recommendations for improvement.

## Features

- Analyzes feature files and step definitions
- Evaluates framework structure and architecture
- Assesses code quality and test coverage
- Provides Selenium implementation analysis
- Generates detailed PDF reports
- Offers both web interface and REST API

## Requirements

- Java 17 or higher
- Maven 3.6 or higher

## Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/bdd-framework-analyzer.git
cd bdd-framework-analyzer
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The application will be available at `http://localhost:5000`

## Usage

### Web Interface

1. Open `http://localhost:5000` in your browser
2. Enter the path to your BDD framework project
3. Click "Analyze" to start the analysis
4. View the results and download the PDF report

### REST API

#### Analyze Project

```bash
curl -X POST http://localhost:5000/api/analyze \
  -H "Content-Type: application/json" \
  -d '{"project_path": "/path/to/your/project"}'
```

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── bddanalyzer/
│   │           ├── controller/
│   │           ├── model/
│   │           └── service/
│   └── resources/
│       ├── static/
│       ├── templates/
│       └── application.properties
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details. 