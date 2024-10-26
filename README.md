# ZEOTAP ASSIGNMENT
## Table of Contents

1. ### [Rule Engine with AST](#rule-engine-with-ast)  
   1.1 [Overview](#overview)  
   1.2 [Objective](#objective)  
   1.3 [Architecture](#architecture)  
   1.4 [Features](#features)  
   1.5 [API Design](#api-design)  
      1.5.1 [create_rule](#create_rule)  
      1.5.2 [combine_rules](#combine_rules)  
      1.5.3 [evaluate_rule](#evaluate_rule)  
   1.6 [Docker Setup](#docker-setup)  
      1.6.1 [Running the Application](#running-the-application)  
   1.7 [Getting Started Without Docker](#getting-started-without-docker)  
      1.7.1 [Manual Installation](#manual-installation)  

2. ### [Real-Time Data Processing System for Weather Monitoring](#real-time-data-processing-system-for-weather-monitoring)  
   2.1 [Overview](#overview-1)  
   2.2 [Objective](#objective-1)  
   2.3 [Data Source](#data-source)  
   2.4 [Features](#features-1)  
   2.5 [Getting Started](#getting-started)  
      2.5.1 [Manual Installation (Without Docker)](#manual-installation-without-docker)  
   2.6 [API Design](#api-design-1)  
      2.6.1 [get_alerts](#get_alerts)  
      2.6.2 [create_alerts](#create_alerts)  
      2.6.3 [delete_alert](#delete_alert)  
   2.7 [Configuration](#configuration)  
   2.8 [Frontend Setup](#frontend-setup)  
   2.9 [Usage](#usage)

# Rule Engine with AST

## Overview
This project is a simple 3-tier rule engine application designed to determine user eligibility based on attributes such as age, department, income, spend, etc. The system leverages an **Abstract Syntax Tree (AST)** to represent conditional rules dynamically. The rule engine allows for the creation, combination, and modification of rules based on user-defined conditions.

## Objective
The goal of this application is to create a flexible rule engine where rules can be built, combined, and evaluated to determine eligibility criteria for users. This is achieved using AST to represent these rules dynamically, providing a structure that can easily be manipulated and expanded.

## Architecture
The project follows a **3-tier architecture**:
1. **Frontend (UI)**: A simple interface where users can define rules and evaluate user eligibility based on attributes.
2. **API Layer**: Serves as the middle layer, handling user requests and interactions between the frontend and backend.
3. **Backend (Data)**: Manages the rule logic, including rule creation, combination, and evaluation using AST.

## Features
- **Rule Creation**: Users can define individual rules based on user attributes (e.g., age, income).
- **Rule Combination**: Multiple rules can be combined using logical operators (AND, OR) to form more complex eligibility conditions.
- **AST Representation**: Every rule and combination of rules is represented as an AST, enabling flexibility in rule manipulation.
- **Dynamic Evaluation**: User eligibility can be evaluated dynamically based on the provided rules and input data.

## API Design
1. **create_rule(rule_string)**:  
   This function takes a string representing a rule & rule name. It returns a `Node` object representing the corresponding AST.

   Example:
   ```bash
   https://rule-engine-64c2.onrender.com/api/v1/rules/create
   ```
   
   ```bash
   {
    "rule": "((age > 50 AND department = 'Tax') OR (age < 21 AND department = 'Employee')) AND (salary > 60000 OR experience > 6)",
    "name": "Rule1"
   }
   ```
   **Sample Response**
   ```bash
   {
    "success": true,
    "message": "AST Tree Created Successfully and saved",
    "AST": {
        "rule_id": 25,
        "name": "Rule8",
        "ast": {
            "left": {
                "left": {
                    "left": {
                        "left": null,
                        "type": "operand",
                        "right": null,
                        "value": "age > 50"
                    },
                    "type": "AND",
                    "right": {
                        "left": null,
                        "type": "operand",
                        "right": null,
                        "value": "department = 'Tax'"
                    },
                    "value": null
                },
                "type": "OR",
                "right": {
                    "left": {
                        "left": null,
                        "type": "operand",
                        "right": null,
                        "value": "age < 21"
                    },
                    "type": "AND",
                    "right": {
                        "left": null,
                        "type": "operand",
                        "right": null,
                        "value": "department = 'Employee'"
                    },
                    "value": null
                },
                "value": null
            },
            "type": "AND",
            "right": {
                "left": {
                    "left": null,
                    "type": "operand",
                    "right": null,
                    "value": "salary > 60000"
                },
                "type": "OR",
                "right": {
                    "left": null,
                    "type": "operand",
                    "right": null,
                    "value": "experience > 6"
                },
                "value": null
            },
            "value": null
        }
    }
    }
2. **combine_rules(rules)**:
This function takes multiple rule strings and combines them into a single AST using the operator provided. The function returns the root node of the combined AST.

  Example: 
  ```bash
  https://rule-engine-64c2.onrender.com/api/v1/rules/combine
   ```
   
   ```bash
   {
    "rules": [
        "((age > 50 AND department = 'Tax') OR (age < 21 AND department = 'Employee')) AND (salary > 60000 OR experience > 6)",
        "((age <25 50 OR department = 'Income') OR (age > 51 OR department = 'Employee')) OR (salary > 60000 OR experience < 8)"
    ],
    "operator": "AND"
  }
   ```
**Sample Response**
```bash
{
    "success": true,
    "message": "AST Tree Created Successfully and saved",
    "AST": {
        "rule_id": 26,
        "name": "combined1729621685154",
        "ast": {
            "left": {
                "left": {
                    "left": {
                        "left": {
                            "left": null,
                            "type": "operand",
                            "right": null,
                            "value": "age > 50"
                        },
                        "type": "AND",
                        "right": {
                            "left": null,
                            "type": "operand",
                            "right": null,
                            "value": "department = 'Tax'"
                        },
                        "value": null
                    },
                    "type": "OR",
                    "right": {
                        "left": {
                            "left": null,
                            "type": "operand",
                            "right": null,
                            "value": "age < 21"
                        },
                        "type": "AND",
                        "right": {
                            "left": null,
                            "type": "operand",
                            "right": null,
                            "value": "department = 'Employee'"
                        },
                        "value": null
                    },
                    "value": null
                },
                "type": "AND",
                "right": {
                    "left": {
                        "left": null,
                        "type": "operand",
                        "right": null,
                        "value": "salary > 60000"
                    },
                    "type": "OR",
                    "right": {
                        "left": null,
                        "type": "operand",
                        "right": null,
                        "value": "experience > 6"
                    },
                    "value": null
                },
                "value": null
            },
            "type": "OR",
            "right": {
                "left": {
                    "left": {
                        "left": {
                            "left": null,
                            "type": "operand",
                            "right": null,
                            "value": "age < 25"
                        },
                        "type": "OR",
                        "right": {
                            "left": null,
                            "type": "operand",
                            "right": null,
                            "value": "department = 'Income'"
                        },
                        "value": null
                    },
                    "type": "OR",
                    "right": {
                        "left": {
                            "left": null,
                            "type": "operand",
                            "right": null,
                            "value": "age > 51"
                        },
                        "type": "OR",
                        "right": {
                            "left": null,
                            "type": "operand",
                            "right": null,
                            "value": "department = 'Employee'"
                        },
                        "value": null
                    },
                    "value": null
                },
                "type": "OR",
                "right": {
                    "left": {
                        "left": null,
                        "type": "operand",
                        "right": null,
                        "value": "salary > 60000"
                    },
                    "type": "OR",
                    "right": {
                        "left": null,
                        "type": "operand",
                        "right": null,
                        "value": "experience < 8"
                    },
                    "value": null
                },
                "value": null
            },
            "value": null
        }
    }
  }
```

3. **evaluate_rule(JSON data)**:
This function takes a JSON representing the combined rule's AST and a dictionary containing user attributes.
The function evaluates the rule against the provided data and returns **True** if the user is eligible based on the rule, **False** otherwise.

  Example:
  ```bash
  https://rule-engine-64c2.onrender.com/api/v1/rules/evaluate
  ```

  ```bash
  {
    "ast": "((age > 50 AND department = 'Tax') OR (age < 21 AND department = 'Employee')) AND (salary > 60000 OR experience > 6)",
    "data": {
        "age": 35,
        "department": "Sales",
        "salary": 60000,
        "experience": 3
    }
  }
  ```
**Sample Response**
```bash
{ "success":true,
  "result":false
}
```
## Docker Setup

### Prerequisites
- **Docker**: Ensure Docker is installed on your machine. You can download it from [Docker's official site](https://www.docker.com/get-started).
- **Docker Compose**: Ensure Docker Compose is also installed.

### Running the Application
   ***Backend Setup***
1. Clone the repository:
   ```bash
   git clone https://github.com/Mynkara08/ZeotapAssignment
   ```
2. Navigate to the project directory:
   ```bash
   cd Assignment-1
   cd zeotap_rule_engine_backend
   ```
3. Ensure you have the docker-compose.yml file in the root directory.
4. Start the application using Docker Compose:
   ```bash
   docker-compose up
   ```
5. Docker will pull the required images (if not already available) and start the services defined in the docker-compose.yml file.
6. To stop the application, use:
   ```bash
   docker-compose down
   ```
***Frontend Setup***
1. Navigate to the frontend directory:
   ```bash
   cd Assignment-1
   cd Frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the frontend development server:
   ```bash
   npm start
   ```
4. The frontend will be running on http://localhost:3000/ by default. You can access it through your browser.

## Getting Started Without Docker

### Prerequisites
- **Java Development Kit (JDK)**: Ensure you have JDK 11 or higher installed. You can download it from the [Oracle website](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) or [AdoptOpenJDK](https://adoptopenjdk.net/).
- **Maven**: Make sure Maven is installed to manage project dependencies. You can download it from the [Maven website](https://maven.apache.org/download.cgi).

### Manual Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Mynkara08/ZeotapAssignment
   ```
2. Navigate to the backend directory
   ```bash
   cd Assignment-1
   cd zeotap_rule_engine_backend
   ```
3. Build the project and install dependencies:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```
5. The Spring Boot application should now be running on http://localhost:8080



# Real-Time Data Processing System for Weather Monitoring

## Overview

The **Real-Time Data Processing System for Weather Monitoring** is designed to continuously monitor and provide summarized insights into weather conditions. By leveraging the OpenWeatherMap API, the system retrieves various weather parameters and presents them in an easily digestible format, including rollups and aggregates for enhanced analysis.

## Objective

The primary objective of this project is to develop a robust real-time data processing system that can:

- Monitor weather conditions in real time.
- Summarize insights using rollups and aggregates.
- Provide a user-friendly interface for displaying current weather, averages, daily summaries, and alerts.

## Data Source

The system utilizes data from the [OpenWeatherMap API](https://openweathermap.org/). You will need to sign up for a free API key to access the data. The API provides several weather parameters, including:

- **main**: Main weather condition (e.g., Rain, Snow, Clear)
- **temp**: Current temperature in Centigrade
- **feels_like**: Perceived temperature in Centigrade
- **dt**: Time of the data update (Unix timestamp)

## Features

- **Current Weather Display**: Showcases the current weather conditions.
- **Weather Averages Chart**: Visual representation of weather averages over time using Chart.js.
- **Daily Weather Summary**: Allows users to select a city and date range to fetch and display daily summaries.
- **Recent Alerts Section**: Displays any recent weather alerts received from the API.

## Getting Started

### Prerequisites

- Docker Daemon
- Node.js
- NPM
- An OpenWeatherMap API key (sign up [here](https://openweathermap.org/))

### Installation

***Backend Setup***
1. Clone the repository:

   ```bash
   git clone https://github.com/Mynkara08/ZeotapAssignment
   ```

2. Navigate to the project directory:

   ```bash
   cd Assignment-2
   cd Realtime-Weather-Monitoring-Backend
   ```

3. Ensure you have the docker-compose.yml file in the root directory.
4. Create a .env file in the root directory with the following content:
   ```bash
   PG_HOST=db
   PG_USER=your_pg_user
   PG_PASSWORD=your_pg_password
   PG_DATABASE=your_pg_database
   PG_PORT=5432
   API_KEY=your_api_key //Api Key in Submitted PDF
   PORT=your_app_port
   USER_EMAIL=your_email@example.com // Email in Submitted PDF
   USER_PASS=your_secure_password // password in Submitted PDF
   ```
5. Start the application using Docker Compose:
   ```bash
   docker-compose up
   ```
6. Docker will pull the required images (if not already available) and start the services defined in the docker-compose.yml file.
7. To stop the application, use:
   ```bash
   docker-compose down
   ```
### Manual Installation (Without Docker)

1. Clone the repository:
   ```bash
   git clone https://github.com/Mynkara08/ZeotapAssignment
   ```
2. Navigate to the backend directory
   ```bash
   cd Assignment-2
   cd Realtime-Weather-Monitoring-Backend
   ```
3. Build the project and install dependencies:
   ```bash
   npm install
   ```
4. Run the application:
   ```bash
   node server.js
   ```
5. The application should now be running on http://localhost:8080

## API Design

1. **(get_alerts)**
   
   ```bash
   http://localhost:6969/get-alerts
   ```
   Sample Response:

   ```bash
   {
    "success": true,
    "message": "List of All Alerts",
    "data": [
        {
            "id": 15,
            "email": "mayunksingh@gmail.com",
            "thresold": 22,
            "city": "Hyderabad"
        }
    ]
   }
   ```
2. **(create_alerts)**
   
   ```bash
   https://weather-analysis-hhcy.onrender.com/add-alert
   ```

   ```bash
   {
    "email":"prashanbhu@gmail.com",
     "thresoldTemperature":32,
      "city":"Bengaluru"
   }
   ```

   Sample Response:

   ```bash
   {
    "success": true,
    "message": "Your Alert is Successfully created",
    "data": [
        {
            "id": 16,
            "email": "prashanbhu@gmail.com",
            "thresold": 32,
            "city": "Bengaluru"
        }
    ]
   }
   ```
3. **(delete_alert)**
  
   ```bash
   https://weather-analysis-hhcy.onrender.com/remove-alert/16
   ```

   Sample Response:
   ```bash
   {
    "success": true,
    "message": "your alert is Removed Successfully",
    "data": []
   }
   ```
***Frontend Setup***
1. Install Live Server from ([extensions](https://marketplace.visualstudio.com/items?itemName=ritwickdey.LiveServer)
2. Start the live server.
3. You can view the webpage in your browser at http://127.0.0.1:5500/index.html
## Usage

- Select a city from the dropdown menu to view the current weather and fetch daily summaries.
- Use the date picker to specify the date range for the daily weather summary.
- The application will display weather alerts in real time.



