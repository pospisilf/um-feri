# AIS Project PoC

This is a simple Node.js project designed to demonstrate a DevSecOps pipeline using:
- **SonarCloud** for code analysis.
- **Snyk** for dependency vulnerability scanning.
- **Dependabot** for automated dependency updates.

## Dependencies

| Dependency | Version | Status |
|------------|---------|--------|
| `chalk`    | 2.4.1   | Outdated |
| `lodash`   | 4.17.10 | Vulnerable |
| `express`  | 4.16.0  | Outdated |
| `mocha`    | 5.2.0   | Outdated |

## How to Run

1. Install dependencies:
   ```bash
   npm install
   ```

2. Start the application:
   ```bash
   npm start
   ```

3. Access application in web browser:
   ```bash
   http://localhost:3000
   ```

## DevSecOps Tools
This project is integrated with the following tools:

- SonarCloud for analyzing code quality and security.
- Snyk for identifying vulnerabilities in dependencies.
- Dependabot for automating dependency updates
