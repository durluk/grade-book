#Java
version - 11

#EDNPOINTS
##POST
{url}/api/v1/school-subjects
Example request body:
```json
[
    "Geografia",
    "Matematyka",
    "język polski"
]
```
{url}/api/v1/students
Example request body:
```json
[
    {
        "firstName": "Adam",
        "lastName": "Nowak",
        "schoolYear": "5",
        "street": "Dworcowa",
        "buildingNumber": "1",
        "apartmentNumber": null,
        "postalCode": "77-777",
        "city": "Wrocław",
        "pesel": "12345678912",
        "birthDate": "1999-12-22",
        "dyslexic": false
    }
]
```
{url}/api/v1/{id}/student/grades
Example request body:
```json
[
    {
        "subject": "Matematyka",
        "grade": "2"
    },
    {
        "subject": "Matematyka",
        "grade": "4-"
    }
]
```
##GET
{url}/api/v1/school-subjects
Example response:
```json
[
    {
        "name": "Geografia"
    },
    {
        "name": "Matematyka"
    },
    {
        "name": "język polski"
    }
]
```
{url}/api/v1/students
Example response:
```json
[
    {
        "firstName": "Adam",
        "lastName": "Nowak",
        "externalId": "88e7be41-ac17-464a-9fc3-5798118d8a19",
        "schoolYear": 5,
        "dyslexic": false
    }
]
```
{url}/api/v1/{id}/student
Example response:
```json
{
    "firstName": "Adam",
    "lastName": "Nowak",
    "externalId": "88e7be41-ac17-464a-9fc3-5798118d8a19",
    "schoolYear": 5,
    "dyslexic": false,
    "pesel": "12345678912",
    "birthDate": "2000-12-22",
    "street": "Dworcowa",
    "buildingNumber": "1",
    "apartmentNumber": null,
    "postalCode": "77-777",
    "city": "Wrocław"
}
```
{url}/api/v1/{id}/student/grades
Example response:
```json
{
    "studentInfo": {
        "firstName": "Adam",
        "lastName": "Nowak",
        "externalId": "88e7be41-ac17-464a-9fc3-5798118d8a19",
        "schoolYear": 5,
        "dyslexic": false
    },
    "grades": [
        {
            "subjectName": "Matematyka",
            "grades": [
                "2",
                "4-"
            ]
        }
    ]
}
```