<!--
@bot-written

WARNING AND NOTICE
Any access, download, storage, and/or use of this source code is subject to the terms and conditions of the
Full Software Licence as accepted by you before being granted access to this source code and other materials,
the terms of which can be accessed on the Codebots website at https://codebots.com/full-software-licence. Any
commercial use in contravention of the terms of the Full Software Licence may be pursued by Codebots through
licence termination and further legal action, and be required to indemnify Codebots for any loss or damage,
including interest and costs. You are deemed to have accepted the terms of the Full Software Licence on any
access, download, storage, and/or use of this source code.

BOT WARNING
This file is bot-written.
Any changes out side of "protected regions" will be lost next time the bot makes any changes.
-->

# Fish Entity

Details regarding a given Fish.


## Special Attributes
| Name | Description |
| ---- | ---- |
| CRUD | Allows for management of data. CRUD stands for create, read, update and delete. |
| Export | Allows for data to be exported. |

## Attributes
| Name | Type | Example | Required | Rules | Description |
| ---- | :----: | :--------: | :-----: | ----- | ----- |
| Name | STRING | Sally | <i class="fa fa-check"> | <ul><li>Attribute must be entered.</li></ul> | The fish's name. | 
| DateOfBirth | DATE | 2018-10-22 | <i class="fa fa-times"> | <ul></ul> | The fish's birthday. | 
| Alive | BOOLEAN | true | <i class="fa fa-times"> | <ul></ul> | Is the fish alive? | 
| Born | STRING | Sally | <i class="fa fa-times"> | <ul></ul> | Where the fish born. | 


## Security
| Group  | Create | Read | Update | Delete |
| ---- | :----: | :----:  | :----:  | :----:  |
| Fishnatic | <i class="fa fa-check"> | <i class="fa fa-check"> | <i class="fa fa-check"> | <i class="fa fa-check"> |
| Admin | <i class="fa fa-check"> | <i class="fa fa-check"> | <i class="fa fa-check"> | <i class="fa fa-check"> |

## List of Records

The list of records provided a means to see all of the Fish found in the system. It is broken into pages with navigation between the pages available at the bottom of the list.

The list can be sorted based the the various attributes associated with the Fish Entity by clicking the columns to sort the list either ascending or desending.

Additional operations can be performed on the list such as search and filtering by making use of the functions at the top of the list. Additionally, actions can be performed against individual records (where applicable),
by making use of the buttons situated on the right of each row.

## Actions
### Create

1. To create a new Fish Entity entry click "Create new"
2. Fill in the fields so that they match the the rules defined above.
3. Click Submit.