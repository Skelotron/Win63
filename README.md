# Win63 (In progress)

Actually this is not fully implemented (especially front-end), but I believe there is enough code examples available here.

## Short Description
This is a client-server java program based on Spring Boot.
The main purpose is to allow to parse and collect data from online shop. After it user can subscribe to new items choosen by criterias specified by user.
System will perform updates by schedule and notify users about new items.

## Usage Example
For example we want to get emails if there are new Cell Phones with Price less than 50 000 and sellers are located in Samara.
User need to create subscription. Something like this:

### User Steps
Notification Type: Email
Email: testemail@gmail.com
Subject: New Item <ItemTitle>
Message: New Item <ItemTitle> is available at a price <ItemCost> click here to open it <ItemUrl>.

Filters:
1) Item Category = Cell Phones
2) Item Price < 50 000
3) Item City = Samara

### System steps
System will retrieve data for each category which have subscriptions.
After it new items will match with existing subscription.
In case of matched subscription system will construct message, populate values in tags and send it to notification service.
Notification Service will send it via specified transport (currently only Email)

## Main Code Points
ItemSynchronizationTask - scheduled job to synchronize Items
CategorySynchronizationTask - scheduled job to synchronize Categories
ItemFilterChecker, AbstractFilterChecker - filter match logic
CategoryConverter, ItemConverter, PhotoConverter - provide a way to convert online-shop entries to system entities
