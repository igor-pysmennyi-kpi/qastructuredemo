# This is the test task for automation QAs:

We have created a dynamically growing competitor to github's gist.
Thanks to our effort users can create notes which are valid until some date (see api spec), assign them categories (PROMO or INFO now, but we looking forward for more in our bright future!),
update already created ones, view all or valid until some date (with paging support) and not the day more. even DELTE is supported! 
We are constantly evolving with the marked so need to adapt quickly and our glorious devopses would like to know does these
changes break anything. So now it's time to hire an automation QA.


API documentation of our secure service can be found [here](https://www.getpostman.com/collections/726828d7c5308e3f7a31)
(Postman collection)


We expect that you will write tests suitable for automation and add them to our test package. Also we hope that you come 
up with ideas on our almost perfect API (with motivation, of course) and write them all to text file. And will add this file to the pull request to this repo together 
with implemented tests. 

To run app via maven:
    <pre>mvn clean spring-boot:run</pre>

To run tests via maven:
    <pre>mvn clean verify</pre>