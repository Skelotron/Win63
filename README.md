# Win63 (In progress)

Actually this is not fully implemented (especially front-end), but I believe there is enough code examples available here.

## Short Description
This is a client-server java program based on Spring Boot.<br>
The main purpose is to allow to parse and collect data from online shop. After it user can subscribe to new items choosen by criterias specified by user.<br>
System will perform updates by schedule and notify users about new items.<br>

## Usage Example
For example we want to get emails if there are new Cell Phones with Price less than 50 000 and sellers are located in Samara.<br>
User need to create subscription. Something like this:<br>

### User Steps
Notification Type: Email<br>
Email: testemail@gmail.com<br>
Subject: New Item <ItemTitle><br>
Message: New Item <ItemTitle> is available at a price <ItemCost> click here to open it <ItemUrl>.<br>

Filters:
1) Item Category = Cell Phones
2) Item Price < 50 000
3) Item City = Samara

### System steps
System will retrieve data for each category which have subscriptions.<br>
After it new items will match with existing subscription.<br>
In case of matched subscription system will construct message, populate values in tags and send it to notification service.<br>
Notification Service will send it via specified transport (currently only Email)<br>

## Main Code Points
ItemSynchronizationTask - scheduled job to synchronize Items<br>
CategorySynchronizationTask - scheduled job to synchronize Categories<br>
ItemFilterChecker, AbstractFilterChecker - filter match logic<br>
CategoryConverter, ItemConverter, PhotoConverter - provide a way to convert online-shop entries to system entities
