# R18A_Group3

About how to run the program:
First, pull the whole file from git master branch, then use gradle to build and run the program.
After the program run, it will shows the dates at the top, then will let you make a selection in the following opinion(1.Normal user, 2.Admin, 3.Next Day, and 4. quit).(The Next Day command is used to go to the next date.)


Normal user:
1.Display the currency table - can let user to check the exchange rates between 4 most popular currencies as a table.

2.Convert the currency - can let user to select the currency to convert, while user enter the amount of the currency they want to convert, the result in this format e.g. 1 AUD = 5 CNY.(usage: 1.input currency you want to convert from 2. input amount of the currency 3. input the currency you want to convert to).

3.Show summary of conversion - the user can check the summary of the conversion rates of 2 currencies user choose within a specific duration (start and end dates). This summary includes all conversion rates in the history, average, median, maximum, minimum and standard deviation of the conversion rate of the 2 currencies during the specified start and end date. (usage: 1.input currency from. 2.input the currency to. 3. input the start date of the selected period. 4.input the end date of the selected period.)

4.Back - back to period stage.

Admin:

Admin can use all user method as shown above and also do the below method.

1.Add a new currency - can add new currency to the database(enter example: < new currency name >).

2.Add a new exchange - can add new exchange rates daily by entering the date and exchange rate for that date of all currencies stored in the file.1.input currency from. 2.input the currency to 3. input the new rate.(input the String one by one)

3.Back - back to period stage.



How to test the program:
use junit to write the testcase to test the program's output.

Contribute/collaborate on the codebase for each group member:

Tool management(Jerkins): Jerry Li

Time/Currency/Exchange/Month: JIESHENG(Jackson) LIANG

Admin: Jacky Li, Jackson Cai

Normal User: Chenglong(Jerry) Li, Lucius

Product integration: All members

Product test: All members
