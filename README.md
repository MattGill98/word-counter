# Word Counter App

This is an application designed to count words from either a file or URL. The definition of a word has been based on the word delimiters between them. I've referred to https://en.wikipedia.org/wiki/Word_count for my research, as well as modified the criteria to make sure the example given passes as well.

NOTE: The application contains a `view` package that has been used to create a REST API as an additional feature. This is in no way necessary for the core implementation of the API, but is a nice extra.

# System Requirements

- Java 15
- Maven 3.6.3

# Quick Start

## Java API

The module exported contains a fluent API to perform the basic requirements of the app. See the examples below for the basics:

Code:
~~~java
new WordCounter()
    .read("Hello world & good morning. The date is 18/05/2016")
    .summarise();
~~~

Output:
~~~
Word count = 9
Average word length = 4.556
Number of words of length 1 is 1
Number of words of length 2 is 1
Number of words of length 3 is 1
Number of words of length 4 is 2
Number of words of length 5 is 2
Number of words of length 7 is 1
Number of words of length 10 is 1
The most frequently occurring word length is 2, for word lengths of 4 & 5
~~~

Code:
~~~java
new WordCounter()
    .read(new URI("http://janelwashere.com/files/bible_daily.txt"))
    .summarise();
~~~

Output:
~~~
Word count = 46824
Average word length = 3.978
Number of words of length 1 is 1044
Number of words of length 2 is 7228
Number of words of length 3 is 14025
Number of words of length 4 is 10926
Number of words of length 5 is 5874
Number of words of length 6 is 3055
Number of words of length 7 is 2162
Number of words of length 8 is 1225
Number of words of length 9 is 820
Number of words of length 10 is 252
Number of words of length 11 is 144
Number of words of length 12 is 46
Number of words of length 13 is 13
Number of words of length 14 is 7
Number of words of length 15 is 3
The most frequently occurring word length is 14025, for word lengths of 3
~~~

## REST API

A REST API has been provided (it's beyond the scope of the task but may be useful in testing). A Swagger UI has also been deployed to help access this. To use the API, You must build the application and start the server.

Using Maven, build the application:

~~~bash
mvn clean package
~~~

Next, run the server:
~~~bash
java -jar target/word-counter-1.0-SNAPSHOT-microbundle.jar --nocluster
~~~

Finally, visit `http://localhost:8080/api/openapi-ui`. From here you will find the Swagger UI which describes the REST API.

Note: passing both the `text` and `uri` parameters to the REST API will cause both to be parsed. Make sure that if you want accurate results for only one of them, only one of them is provided.