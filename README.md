# BankATM

Qi Yin

U31787103

# How to run:
The entrance of the program is controller/BankATM.java. You can run this program in src folder by

	javac controller/BankATM.java
	java controller/BankATM

# Run Instructions

## Bank ATM
1. Click **`User`** to enter user system.<br>
2. Click **`Manager`** to enter manager system.<br>

## Manager Login
1. Enter valid **`Username`** and **`Password`**, then click **`Login`** to login.<br>
2. Click the **`back`** button in the upper left corner to return to the Bank ATM page.
3. Default username is "Admin" and default password is "admin". This is defined in Config.java.

## User Login
1. Enter valid **`Username`** and **`Password`**, then click **`Login`** to login.<br>
2. Click **`Register`** to register for a user.
3. Click the **`back`** button in the upper left corner to return to the Bank ATM page.

## Manager System
1. Click **`Check Balance`** to check the bank's asset. This only contains the money the bank earned(service fee).
2. Click **`Check Customer`** to check the customers.
3. Click **`Get Daily Report`** to view the daily report.
4. Click **`Set Config`** to set some configurations.
5. Click **`Hand Interest`** to hand interest for customers' saving accounts. A day's interest would be credited to user's account.
6. Click the **`back`** button in the upper left corner to return to the Manager Login page.
7. Click the **`Logout`** button in the upper right corner to return to the Manager Login page.

## Check Customer
1. Enter **`Username`**, Choose **`Sort By`**, **`Sort`**, then click **`Search`** to search a user or sort the users.
2. Click **`Detail`** to see the details for a user.
3. Click the **`back`** button in the upper left corner to return to the Manager System page.
4. Click the **`Logout`** button in the upper right corner to return to the Manager Login page.

## User Detail
1. Choose **`Number`** to check the details in different accounts for user.
2. Choose **`View Detail`** to see a specific transaction detail.
3. Click **`Pass`** to pass a loan request.
4. Click the **`back`** button in the upper left corner to return to the Check Customer page.
5. Click the **`Logout`** button in the upper right corner to return to the Manager Login page.

## Set Configuration
1. Enter **`Open Account Fee`**, **`Close Account Fee`** to change the default settings. The default settings can be changed in Config.java.
2. Click **`Add`** to add a new crrency's configuration.
3. Click **`Edit`** to edit the existed currency's configuration.
4. Click **`Save`** the save the changes.
5. Click **`Cancel`** to return to Manager System page.

## Currency Config
1. If add a new currency, enter valid **`Name`**, **`Service Fee Rate`**, **`Saving Acc Interest Rate`**, **`Loan Interest Rate`**, **`Lowest Balance For Interest`**, then click **`Save`** to create a new currency. All blanks are manadatory.
2. If edit a existed currency, change **`Service Fee Rate`**, **`Saving Acc Interest Rate`**, **`Loan Interest Rate`**, **`Lowest Balance For Interest`**, then click **`Save`** to change a the currency's configuration. **`Name`** cannot be changed.
3. Click **`Cancel`** to return to Set Configuration page.

## User Register
1. Enter valid **`First Name`**, **`Middle Name`**, **`Last Name`**, **`Username`**, **`Sex`**, **`Phone Number`**, **`Email`**, **`Birthday`**, **`Password`**, **`Confirm Password`**, then click **`Register`** to register a new user.
2. All blanks are mandatory except **`Middle Name`**.
3. **`Phone Number`** only contains numbers.
4. **`Email`** format is xxxx@xx.xx.
5. **`Birthday`** format is mm/dd/yyyy.
6. **`Password`** and **`Confirm Password`** should match with each other.
7. Click **`Cancel`** to return to User Login page.

## User System
1. Click **`Create`** to create a saving account or a checking account. User should pay for their open account fee with default currency every time they create an account.
2. Click **`Deposit`** to make a deposit.
3. Click **`Withdraw`** to make a withdrawal.
4. Click **`Transfer`** to make a transfer.
5. Click **`Show Detail`** to show details of accounts.
6. Click **`Take Loan`** to take a loan.
7. Click **`Pay`** to pay for the loan.
8. Click the **`back`** button in the upper left corner to return to the User Login page.
9. Click the **`Logout`** button in the upper right corner to return to the User Login page.

## Deposit
1. Choose **`To Account`**, **`Currency`**, enter **`Amount`**, **`Remarks`**, then click **`Confirm`** to finish the deposit. User deposits with their cash.
2. All blanks are mandatory except **`Remarks`**.
3. Click **`Cancel`** to return to User System page.

## Withdraw
1. Choose **`From Account`**, **`Currency`**, enter **`Amount`**, **`Remarks`**, then click **`Confirm`** to finish the withdrawal. User withdraws money from their accounts into cash.
2. All blanks are mandatory except **`Remarks`**.
3. Click **`Cancel`** to return to User System page.
4. Amount must less than balance because user should pay for the service fee as well.

## Transfer
1. Choose **`To Account`**, **`From Account`**, **`Currency`**, enter **`Amount`**, **`Remarks`**, then click **`Confirm`** to finish the withdrawal. User transfers money to another account.
2. All blanks are mandatory except **`Remarks`**.
3. Click **`Cancel`** to return to User System page.
4. Amount must less than balance because user should pay for the service fee as well.
5. If From Account is a saving account, To Account can only be choosed in the list and cannot edit. If From Account is a checking account, To Account can be either choosed from the list or inputted with another account number.

## Show Details
1. Choose different **`Account`** to view different accounts' details.
2. Click **`View Detail`** to view a transaction's detail.
3. Click the **`back`** button in the upper left corner to return to the User System page.
4. Click the **`Logout`** button in the upper right corner to return to the User Login page.

## Take Loan
1. Enter valid **`Name`**, **`Collateral`**, **`Currency`**, **`Amount`**, **`Due Date`**, then click **`Confirm`** to take a loan.
2. All blanks are mandatory.
3. **`Due Date`** format is mm/dd/yyyy.
4. Click **`Cancel`** to return to User System page.

## Pay For Loan
1. Choose **`Pay Account`** for the account that pay for the loan, then click **`Pay`** to pay.
2. Click **`Cancel`** to return to User System page.
3. The loan can be paid only if the status is Passed.