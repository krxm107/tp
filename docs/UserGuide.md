---
layout: page
title: User Guide
---

ClubHub is a fast, keyboard-driven **contact management app** designed for people *who run multiple groups or clubs* — such as gym managers, volunteer coordinators, or hobby group organizers. Instead of juggling messy spreadsheets or address books, ClubHub lets you create, view, search, and organize contacts across different groups entirely from your keyboard.

If you type quickly, you can manage memberships, roles, and clubs faster and more efficiently with ClubHub than with mouse-based apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. Download the latest `.jar` file from [here](https://github.com/AY2526S1-CS2103T-F15b-4/tp/releases).
  
   ![Download guide 1](images/DownloadGuide1.png)
   ![Download guide 2](images/DownloadGuide2.png)

3. Copy the file to the folder you want to use as the _home folder_ for ClubHub.

4. Open a command terminal.

If you're using Windows:

    Press Windows key + R on your keyboard.
    
    A small box will pop up. Type cmd inside it.
    
    Press Enter. A black window will appear — that’s the command terminal. 

If you're using macOS:

    Click the magnifying glass 🔍 in the top-right corner (Spotlight Search).
    
    Type Terminal and press Enter.
    
    A window with text will open — that’s the command terminal.

If you're using Linux:

    Press Ctrl + Alt + T together.
    
    Or search for “Terminal” in your applications.
    
    A terminal window will open.

`cd` into the folder you put the jar file in, and use the `java -jar clubhub.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts and clubs.

   * `add_person n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to ClubHub.

   * `delete_person 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all data.

   * `exit` : Exits the app.

5. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Command keywords are case-insensitive.<br>
  e.g. both `delete_person 2` and `DELETE_Person 2` are acceptable.

* Some commands have both a short keyword version and a long keyword version.<br>
  e.g. `delete_person 2` and `deletep 2` are the same command.

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add_person n/NAME`, `NAME` is a parameter which can be used as `add_person n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

--------------------------------------------------------------------------------------------------------------------


### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

--------------------------------------------------------------------------------------------------------------------

## Adding

### Adding a person: `add_person` (or `addp`)

Adding a person to ClubHub helps you keep your contacts organized and easy to find.
You only need to provide the essential information of name and email, and you can always edit or add more later.

Format: `add_person n/NAME e/EMAIL [p/PHONE_NUMBER] [a/ADDRESS] [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Name and email are both mandatory. Duplicate person names are allowed. Email must be unique to a person.  
</div>

<div markdown="span" class="alert alert-warning">
⚠️ **Warning:**  
Phone numbers are allowed to have letters and some special characters. 
</div>

Command examples:
* `add_person n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `addp n/Betsy Crowe e/betsycrowe@example.com a/Newgate Prison`

![Add Person](images/AddPersonImage.png)

### Adding a club: `add_club` (or `addc`)

Adding a club to ClubHub lets you keep track of different clubs in one place.
You only need to provide the essential information of name and email, and you can always edit or add more later.

Format: `add_club n/NAME e/EMAIL [p/PHONE_NUMBER] [a/ADDRESS] [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Name and email are both mandatory. Club names and email must be unique to a club.
</div>


<div markdown="span" class="alert alert-warning">
⚠️ **Warning:**  
Phone numbers are allowed to have letters and some special characters.
</div>

<div markdown="span" class="alert alert-warning">
⚠️ **Warning:**  
Duplicate clubs that differ only by white spaces are allowed.
</div>

Example: NUS Higher Mother Tongue Club ("NUS HMT Club") is considered a different club from NUS High Mother Tongue Club ("NUSH MT Club").

Command examples:
* `add_club n/Bowling Club p/93456789 e/bowling@example.com a/15 Boon Lay Way`
* `addc n/Cycling e/cycling@example.com`

--------------------------------------------------------------------------------------------------------------------

## Memberships

### Adding multiple persons to multiple clubs : `add_membership`  (or `addm`)

Adds multiple persons to multiple clubs.

Format: `add_membership m/INDEXES c/INDEXES [d/DURATION]`

* The `PERSON_INDEXES` and `CLUB_INDEXES` are space-separated lists of indexes.
* `DURATION` is the duration to extend the expiry date by in months. The duration must be an integer between 1 and 12 inclusive.
* The default duration is 12 months if not specified.
* Join date is set to today’s date.
* Expiry date is set to join date plus duration months.
* Adds the membership of the persons at the specified `PERSON_INDEXES` to the clubs at the specified `CLUB_INDEXES`.
* The indexes refer to the index numbers shown in the displayed person list & club list accordingly. The indexes **must be positive integers** 1, 2, 3, …​

Examples:
* `add_membership m/1 3 c/2 4 d/6` Adds the membership of the 1st and 3rd persons in the 2nd and 4th clubs with expiry date 6 months from today.
  
![Add To](images/AddToImage.png)

### Removing multiple persons from multiple clubs : `delete_membership`  (or `deletem`)

Removes multiple persons from multiple clubs in the club manager.

Format: `delete_membership m/INDEXES c/INDEXES`

* The `PERSON_INDEXES` and `CLUB_INDEXES` are space-separated lists of indexes.
* Removes the membership of the persons at the specified `PERSON_INDEXES` from the clubs at the specified `CLUB_INDEXES`.
* The indexes refer to the index numbers shown in the displayed person list & club list accordingly. The indexes **must be positive integers** 1, 2, 3, …​

Examples:
* `delete_membership m/1 3 c/2 4` Removes the membership of the 1st and 3rd persons in the 2nd and 4th clubs.

### Renew membership of a person in a club : `renew`

Renews the membership of a person in a club with renewal duration given.

Format: `renew m/PERSON_INDEX c/CLUB_INDEX d/DURATION`

* Renew the membership of the person at the specified `PERSON_INDEX` in the club at the specified `CLUB_INDEX`.
* Only active memberships can be renewed.
* `DURATION` is the duration to extend the expiry date by in months. The duration must be an integer between 1 and 12 inclusive.
* The index refers to the index number shown in the displayed person list & club list accordingly. The index **must be a positive integer** 1, 2, 3, …​

Command examples:
* `renew m/1 c/2 d/6` Renews the membership of the 1st person in the 2nd club by 6 months.

### Cancel membership of a person in a club : `cancel`

Cancels the membership of a person in a club.

Format: `cancel m/PERSON_INDEX c/CLUB_INDEX`

* Cancels the membership of the person at the specified `PERSON_INDEX` in the club at the specified `CLUB_INDEX`.
* The index refers to the index number shown in the displayed person list & club list accordingly.
* The index **must be a positive integer** 1, 2, 3, …​
* The membership **remains valid until the expiry date**. This is called **Pending Cancellation** status. The membership cannot be renewed but can be reactivated.
* The Pending Cancellation status will change to Cancelled status when past the expiry date.
* The membership will not be deleted.
* To remove the person from the club, use the `delete_membership` command.

Command examples:
* `cancel m/1 c/2` Cancels the membership of the 1st person in the 2nd club.

![Cancel](images/CancelledImage.png)

### Reactivating membership of a person in a club : `reactivate`

Reactivates expired, pending cancellation, or cancelled membership of a person in a club with duration given.

Format: `reactivate m/PERSON_INDEX c/CLUB_INDEX d/DURATION`

* Reactivates the membership of the person at the specified `PERSON_INDEX` in the club at the specified `CLUB_INDEX`.
* `DURATION` is the duration to extend the expiry date by in months. The duration must be an integer between 1 and 12 inclusive.
* The index refers to the index number shown in the displayed person list & club list accordingly. The index **must be a positive integer** 1, 2, 3, …​
* If expiry date was in the past, setting new expiry date from today.
* If expiry date was in the future, extending from current expiry date.
 
Command examples:
* `reactivate m/1 c/2 d/6` Reactivates the membership of the 1st person in the 2nd club by 6 months.

--------------------------------------------------------------------------------------------------------------------

## Listing and Finding

### Listing all clubs and contacts: `list`

### Finding persons: `find_person` (or `findp`)

Finds and displays persons (in the right list) that match all search conditions specified within the command.
If no condition is provided, `find_person` displays all persons. Possible search conditions include:<br>
* `n/` - by name
* `a/` - by address
* `e/` - by email
* `p/` - by phone
* `t/` - by tag
* `s/` - by membership status<br>
Each search condition is to be supplied with one or more search keywords.<br>

Format: `find_person [SEARCH_CONDITIONS SEARCH_KEYWORDS]...`<br>

<div markdown="span" class="alert alert-info">
ℹ️ **Info:**  
The find_person command is identical to the find_club command in its usage, the only difference being that it
searches for people rather than clubs. Hence, you may refer to the find_club command below for detailed guidance 
on its usage.
</div>

Command examples:
* `findp` returns all persons
* `findp n/ Alex` returns `alex` and `Alex yeoh`
* `findp n/ alex david` returns `alex` and `Alex yeoh` and `David Li`
* `findp n/ alex n/ Yeoh` returns `Alex yeoh`
* `findp n/ Alex t/ friend` returns only `Alex yeoh` because `Alex yeoh` is tagged with `friend` but `alex` is not <br>
  ![result for 'findp n/ Alex t/ friend'](images/findAlexFriendResult.png)

### Finding clubs: `find_club` (or `findc`)

Finds and displays clubs (in the left list) that match all search conditions specified within the command. 
If no condition is provided, `find_club` displays all clubs. Possible search conditions include:<br>
* `n/` - by name
* `a/` - by address
* `e/` - by email
* `p/` - by phone
* `t/` - by tag
* `s/` - by membership status<br>
Each search condition is to be supplied with one or more search keywords.<br>

Format: `find_club [SEARCH_CONDITIONS SEARCH_KEYWORDS]...`<br>

**Basic Usage: Finding by single field**<br>

The find_club command can be used to locate clubs using their basic fields, such as their name, address, email,
phone and tags. To do so, use the matching search condition (as displayed in the previous section) followed 
by the keyword you wish to match for that condition (e.g. `findc n/ Archery`). **Do note that there should be
a space before each search condition or keyword.**<br>

<div markdown="span" class="alert alert-info">
ℹ️ **Info:**  
Can't remember the exact value of the field? Not to worry, find matches keywords by substrings and is case-insensitive,
meaning that calling "findc n/ dance" will return clubs with names like "Dance" and "breakdance".
</div>

**Advanced Usage: Supplying multiple keywords**<br>

Each search condition may be supplied with multiple keywords, **any of** which may be used to match the target. For
example, `findc n/ Monday Tuesday` would return clubs with names like "Monday Dance" and "Tuesday Yoga".<br>

**Advanced Usage: Supplying multiple conditions**<br>

Each command may also be supplied with multiple search conditions. However, unlike the case with keywords, 
**all conditions** must match the target. For example, `findc t/ 8pm n/ Archery n/ Tuesday` will only return clubs
tagged with "8pm" whose names contain both "Archery" and "Tuesday".<br>

**Advanced Usage: Finding by membership status**<br>

The find_club command can be used to locate clubs containing memberships of a particular status, such as clubs with
expired memberships. To do so, use the `s/` condition, **but only with the following keywords:**<br>
* `a` - for active memberships
* `e` - for expired memberships
* `p` - for memberships pending cancellation
* `c` - for canceled memberships<br>

<div markdown="span" class="alert alert-warning">
⚠️ **Warning:**  
Unlike other search conditions, "s/" is able to match all identifiable keywords within a single string of letters.
This means that "findc s/ active" will return all active, expired and canceled members, since the keywords
"a", "c", and "e" are all present.
</div>

**Command examples**<br>

Here are some sample commands:<br>
* `findc` returns all clubs
* `findc n/ Study` returns `study` and `Monday Study`
* `findc n/ Monday study` returns `study` and `Monday Study` and `Monday Guitar`
* `findc n/ Monday n/ study` returns `Monday Study`
* `findc n/ Study t/ 8pm` returns only `study` because `study` is tagged with `8pm` but `Monday Study` is not <br>
  ![result for 'findc n/ Study t/ NTU'](images/findStudyNtuResult.png)

### Listing a person and their associated clubs : `list_memberships` (or `listmp`)

Finds and displays clubs which the specified person is a member of.

Format: `list_memberships INDEX`

* The target person is specified by its `INDEX` shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* In the club list, all clubs associated with the target are displayed
* In the person list, only the target is displayed

Command examples:
* `find_person` followed by `list_memberships 2` lists the 2nd person in the contact list and all clubs which they are a member of.
* `find_person n/ Betsy` followed by `list_memberships 1` lists the 1st person in the results of the `find` command and their associated clubs.

### Listing a club and their members : `list_members` (or `listmc`)

Finds and displays persons which are members of the specified club.

Format: `list_members INDEX`

* The target club is specified by its `INDEX` shown in the displayed club list.
* The index **must be a positive integer** 1, 2, 3, …​
* In the person list, all persons associated with the target are displayed
* In the club list, only the target is displayed

Command examples:
* `find_club` followed by `list_members 2` lists the 2nd club in the club list and all its members.
* `find_club n/ Tennis` followed by `list_members 1` lists the 1st club in the results of the `find` command and its members.

--------------------------------------------------------------------------------------------------------------------

## Editing

### Editing a person : `edit_person`  (or `editp`)

Editing a person in ClubHub helps you keep their
details up to date without having to create a new entry.

Format: `edit_person INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
All fields can be edited to a different case. However, name and email remain mandatory. Duplicate person names are allowed. Email must be unique to a person.  
</div>

<div markdown="span" class="alert alert-warning">
⚠️ **Warning:**  
Phone numbers are allowed to have letters and some special characters.
</div>

Command examples:
*  `edit_person 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `editp 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Editing a club : `edit_club`  (or `editc`)

Editing a club in ClubHub helps you keep club details up to date without having to create a new entry.

Format: `edit_club INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the club at the specified `INDEX`. The index refers to the index number shown in the displayed club list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the club will be removed i.e adding of tags is not cumulative.
* You can remove all the club’s tags by typing `t/` without specifying any tags after it.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Name and email are both mandatory. Club names and email must be unique to a club.
</div>

<div markdown="span" class="alert alert-warning">
⚠️ **Warning:**  
Phone numbers are allowed to have letters and some special characters.
</div>

<div markdown="span" class="alert alert-warning">
⚠️ **Warning:**  
Duplicate clubs that differ only by white spaces are allowed.
</div>

Example: NUS Higher Mother Tongue Club ("NUS HMT Club") is considered a different club from NUS High Mother Tongue Club ("NUSH MT Club").

Command examples:
*  `edit_club 1 p/91234567 e/dance@example.com` Edits the phone number and email address of the 1st club to be `91234567` and `dance@example.com` respectively.
*  `editc 2 n/Bowling t/` Edits the name of the 2nd club to be `Bowling` and clears all existing tags.

--------------------------------------------------------------------------------------------------------------------

## Getting

### Copying a person's details : `get_person` (or `getp`)

Copies the details of a person to the user's clipboard. By default, copies all details apart from memberships. Conditions may be supplied to specify which details to copy.

Format: `get_person INDEX [/OPTIONAL_CONDITIONS]`
Optional conditions:
* `n` specifies the person's name to be included in the copy.
* `p` specifies the person's phone number to be included in the copy.
* `e` specifies the person's email to be included in the copy.
* `a` specifies the person's address to be included in the copy.
* `m` specifies the inclusion of the names of all clubs of which the person is a member of.

* Copies the details of the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* `/` must be used between the index and optional conditions, if any are provided
* Multiple optional conditions may be added by appending each keyword. Non-matching keywords are ignored.
  e.g. `get_person 1 /abcde` will copy the 1st person's address and email to the clipboard.

Command examples:
* `find_person n/ John` followed by `get_person 1 /pe` copies the phone number and email address of the 1st person in the results of the `find` command.
* `find_person` followed by `get_person 2` copies all details (excluding memberships) of the 2nd person in the contact list.
  ![result for 'get_person 2'](images/getPersonResult.png)

### Copying a club's details : `get_club` (or `getc`)

Copies the details of a club to the user's clipboard. By default, copies all details apart from members. Conditions may be supplied to specify which details to copy, including an option to copy all members' details.

Format: `get_club INDEX [/OPTIONAL_CONDITIONS]`
Optional conditions:
* `n` specifies the club's name to be included in the copy.
* `p` specifies the club's phone number to be included in the copy.
* `e` specifies the club's email to be included in the copy.
* `a` specifies the club's address to be included in the copy.
* `m` specifies the inclusion of the names of all clubs members.
* `*` specifies the full details of a club, plus the full details of all its members, to be copied.

* Copies the details of the club at the specified `INDEX`.
* The index refers to the index number shown in the displayed club list.
* The index **must be a positive integer** 1, 2, 3, …​
* `/` must be used between the index and optional conditions, if any are provided
* Multiple optional conditions may be added by appending each keyword. Non-matching keywords are ignored.
  e.g. `get_person 1 /abcde` will copy the 1st club's address and email to the clipboard.

Command examples:
* `find_club n/ Band` followed by `get_club 1 /pe` copies the phone number and email address of the 1st club in the results of the `find` command.
* `find_club` followed by `get_club 2` copies all details (excluding members) of the 2nd club in the club list.
* `find_club` followed by `get_club 2 /*` copies all details (including member details) of the 2nd club in the club list.
  ![result for 'get_club 2 /*'](images/getClubFullResult.png)

### Getting a person's membership history: `get_history` (or `geth`)
Gets and displays the membership history of the specified person in all clubs they have been a member of.
Format: `get_history INDEX`
* The target person is specified by its `INDEX` shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* Displays the membership history of the target person in all clubs they have been a member of.

Examples:
* `get_history 2` displays the membership history of the 2nd person in the contact list.

--------------------------------------------------------------------------------------------------------------------

## Deleting

### Deleting a person : `delete_person` (or `deletep`)

Deletes the specified person from ClubHub.

Format: `delete_person INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Command examples:
* `find_person` followed by `delete_person 2` deletes the 2nd person in the contact list.
* `find_person n/ Betsy` followed by `delete_person 1` deletes the 1st person in the results of the `find_person` command.

### Deleting a club : `delete_club` (or `deletec`)

Format: `delete_club INDEX`

* Deletes the club at the specified `INDEX`.
* The index refers to the index number shown in the displayed club list.
* The index **must be a positive integer** 1, 2, 3, …​

Command examples:
* `find_club` followed by `delete_club 2` deletes the 2nd club in the club list.
* `find_club n/ Tennis` followed by `delete_club 1` deletes the 1st club in the results of the `find_club` command.

--------------------------------------------------------------------------------------------------------------------

## Sorting 

### Sorting persons : `sort_person` (or `sortp`)

Sorts persons in the person list in ascending order of the provided fields.

Format: `sort_person [n/] [p/] [e/] [a/]`

* `n/` sorts the list by person name, `p/` by phone number, `e/` by email, and `a/` by address.
* Any number of fields can be included. The app will sort the list based on the first provided field, with the next field used as a tiebreaker.

Command examples:
* `sort_person n/` sorts the person list based on the ascending alphabetical order of their names.
* `sortp a/ n/` sorts the person list in ascending order of the addresses. For persons with the same address, they will be sorted by their names.

### Sorting clubs : `sort_club` (or `sortc`)

Sorts clubs in the club list in ascending order of the provided fields.

Format: `sort_club [n/] [p/] [e/] [a/]`

* `n/` sorts the list by club name, `p/` by phone number, `e/` by email, and `a/` by address.
* Any number of fields can be included. The app will sort the list based on the first provided field, with the next field used as a tiebreaker.

Command examples:
* `sort_club n/` sorts the club list based on the ascending alphabetical order of their names.
* `sortc a/ n/` sorts the club list in ascending order of the addresses. For clubs with the same address, they will be sorted by their names.

--------------------------------------------------------------------------------------------------------------------

## Others

### Clearing all entries : `clear`

Clears all entries from ClubHub.

Need to confirm with capitalized YES.

Format: `clear YES`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

ClubHub data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

ClubHub data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, ClubHub will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause ClubHub to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Auto scroll to newly added/deleted/edited/changed person or club

When a person or club is changed, the person list or club list will automatically scroll to show the newly changed entry.
If there are multiple entries changed (e.g. when using `add_membership`, `delete_membership`), the list will scroll to show the last entry in the list of edited entries.

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous ClubHub home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format                                                                                                                               | Short form | Examples
--------|--------------------------------------------------------------------------------------------------------------------------------------|------------|------------------|
**Help** | `help`                                                                                                                               
**Add Person** | `add_person n/NAME [p/PHONE_NUMBER] e/EMAIL [a/ADDRESS] [t/TAG]…​` <br>                                                              | `addp`     | `add_person n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665`
**Add Club** | `add_club n/NAME [p/PHONE_NUMBER] e/EMAIL [a/ADDRESS] [t/TAG]…​` <br>                                                                | `addc`     | `add_club n/Basketball Club p/22232434 e/basketball@example.com a/123, Bukit Batok Rd, 1234865`
**Add Membership** | `add_membership m/PERSON_INDEXES c/CLUB_INDEXES [d/DURATION]` <br>                                                                   | `addm`     | `add_membership m/1 2 c/3 4 d/6`
**Delete Membership** | `delete_membership m/PERSON_INDEXES c/CLUB_INDEXES` <br>                                                                             | `deletem`  | `delete_membership m/1 2 c/3 4`
**Renew Membership** | `renew m/PERSON_INDEX c/CLUB_INDEX d/DURATION` <br>                                                                                  |            | `renew m/1 c/2 d/6`
**Cancel Membership** | `cancel m/PERSON_INDEX c/CLUB_INDEX` <br>                                                                                            |            | `cancel m/1 c/2`
**Reactivate Membership** | `reactivate m/PERSON_INDEX c/CLUB_INDEX d/DURATION` <br>                                                                             |            | `reactivate m/1 c/2 d/6`
**Find Person** | `findp [SEARCH_CONDITION_1 SEARCH_KEYWORDS_1] [SEARCH_CONDITION_2 SEARCH_KEYWORDS_2] ... [SEARCH_CONDITION_N SEARCH_KEYWORDS_N]`<br> | `findp`    | `find_person n/ James Jake t/ friend`
**Find Club** | `findc [SEARCH_CONDITION_1 SEARCH_KEYWORDS_1] [SEARCH_CONDITION_2 SEARCH_KEYWORDS_2] ... [SEARCH_CONDITION_N SEARCH_KEYWORDS_N]`<br> | `findc`    | `find_club n/ Dance Guitar t/ monday`
**List Memberships** | `list_memberships INDEX`</br>                                                                                                        | `listmp`   | `list_memberships 1`
**List Members** | `list_members INDEX`</br>                                                                                                            | `listmc`   | `list_members 1`
**Edit Person** | `edit_person INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br>                                                    | `editp`    |`edit_person 2 n/James Lee e/jameslee@example.com`
**Edit Club** | `edit_club INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br>                                                      | `editc`    |`edit_club 2 n/Tennis e/tennis@example.com`
**Get Person** | `get_person INDEX [/OPTIONAL_CONDITIONS]`</br>                                                                                       | `getp`     | `get_person 2 /pae`
**Get Club** | `get_club INDEX [/OPTIONAL_CONDITIONS]`</br>                                                                                         | `getc`     | `get_club 2 /*`
**Get History** | `get_history INDEX`</br>                                                                                                             | `geth`     | `get_history 2`
**Delete Person** | `delete_person INDEX`<br>                                                                                                            | `deletep`  | `delete_person 3`
**Delete Club** | `delete_club INDEX`<br>                                                                                                              | `deletec`  | `delete_club 3`
**Sort Persons** | `sort_person [n/] [p/] [e/] [a/]`</br>                                                                                               | `sortp`    | `sort_person a/ n/`
**Sort Clubs** | `sort_club [n/] [p/] [e/] [a/]`</br>                                                                                                 | `sortc`    | `sort_club a/ n/`
**Clear** | `clear`                                                                                                                              
**Exit** | `exit`                                                                                                                               
