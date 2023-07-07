# BootCounter
App that counts boot events, schedules some actions and posts notifications. On different devices works diffetently,work some minutes before checking result is recommended
“Boot counter” app

The app
The purpose of this app is to track boot completed events and to schedule a recurring task for showing notifications
with boot event info.
Boot event
The application must subscribe to the RECEIVE_BOOT_COMPLETED event.
Once the event is triggered, the application must persist such an event with the relevant information (required in the
next sections).
Application
Any time the application is getting awakened, it must schedule the action to be executed.
General information about the action:
1. It must run every 15 minutes.
2. On run it must show the notification with the “special” body.
3. If the notification is still in the tray from the previous execution, it should be updated with the new data
accordingly.
Note
The title, channel and others missing information does not have any limitations/requirements.
“Special” body
There are 3 possible text within the notification body:
1. If no boot events were detected within the app’s lifetime - the body text should be = “No boots detected”
2. If only 1 boot event was detected, then the text must be = “The boot was detected with the timestamp =
${timestamp_of_the_boot_event}”. “timestamp_of_the_boot_event” event milliseconds since Unix Epoch.
Reference of such here.
3. If multiple events were detected, then the text should be = “Last boots time delta =
${time_between_2_last_boot_events}”. “Time_between_2_last_boot_events” must be the delta between
last and pre-last boot events.

UI
This app has only 1 text view.
It shows different text depending on the information:
1. If no boot events were triggered within the app’s lifetime, then text must be = “No boots detected”
2. If there were boot events during the app’s lifetime then text view must be populated with the info of each boot
event:
a. Number (e.g. 1, 2, 3). 1 is for the first, 2 - for the second etc.
b. Timestamp of the boot event (event milliseconds since Unix Epoch. Reference of such here)

Example of the TextView text:
"1 - 123456789
2 - 12345678910
3 - 12345678911"
