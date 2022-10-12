# Firebase_Lessons_22_23
This app went too far into advanced topics and I needed to scale it back for instruction.
The premise is that this is a Memory Box app.  The user can create an account with firebase auth, and then upload memories
Each memory is given a rating and the rating is chosen with a custom spinner and saved into a Memory object.  The Memory class
implemements Parcelable.  When the user wants to view all memories they are taken to a listView and when they click on a certain
Memory, they are taken to a differen screen where they can see the details of this memory.

Currently this project includes code to have them do an image picker and upload a downloadUrl to the object so that when they view the 
Memory, they can use Picasso to view the Image.

I had wanted to use a recylcer view to displya the images, but it is getting to advanced and way beyond the scope of an "example"

Instead, I am going to have the student project simply have a rating, name, description and then they will be able to edit or delete the 
memories they have for their account.  
