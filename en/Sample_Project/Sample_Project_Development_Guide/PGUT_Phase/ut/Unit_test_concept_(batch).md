# Unit Test Concept (Batch)

The web application of this project defines the test method unique to the project.

（[Unit Test Concept (Web)](Unit_test_concept_(Web).md)）

In principle, batches observe the [Nablarch standard test method](https://nablarch.github.io/docs/LATEST/doc/development_tools/testing_framework/guide/development_guide/05_UnitTestGuide/index.html).

Describes the deliverables created by the developer and how to test them.

| Deliverables         | Theoretical | Class only | Request only |
|----------------|------|------------|----------------|
| Action         |      |            | X              |
| Form           | X    | X          | X              |
| Domain class | X    |            | X              |
| SQL file    | X    |            | X              |
| Entity         |      |            | X              |
| DTO            |      |            | X              |


## Action

In Action class, the operation is checked with request unit test.


## Form

### Confirmation of form annotation

Confirms that the annotation of the form field is as described in the design document.
 (Required Annotation and Domain Annotation)
Compare the design document with the source code of the form and check it visually.


### Confirmation of validation logic

In addition to required and domain annotations, if validation logic is written using `@AssertTrue`, etc., 
then it will be targeted for class unit test.

## Domain class

This class holds the domain definition and creates a domain definition design form.

When a domain class is created from the domain definition design document, there is a possibility of incorrect definitions (for example, the maximum digit is copied incorrectly).
Check by theoretical review as it is inefficient to write a test code based on limit value analysis for detecting the error.



## SQL file

It is necessary to consider SQL in the design phase, but if you design SQL theoretically, there is a problem that quality cannot be sufficiently ensured. 
For this reason, it is assumed that the design will be carried while testing with SQL, and the SQL file is created at the same time. 
 (This SQL file will be input to the Programming and Unit testing phase)

SQL Executor is the tool used to test SQL.


## Entity

As it is automatically generated by the GSP plugin, it is excluded from the class unit test.

## DTO

DTO is excluded from the class unit test as it does not contain any logic and the accessor is automatically generated by the IDE.

