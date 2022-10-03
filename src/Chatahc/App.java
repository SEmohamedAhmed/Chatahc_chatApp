package Chatahc;
import java.sql.*;
import java.util.*;
import static GUI.Main.utilities;
public class App {
    Connection con;
    PreparedStatement preQuery;
    String query;
    public App() throws SQLException {
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chatahc", "root", "hello");
       Statement st = con.createStatement();
    }
    public void registerForUser(User user) throws SQLException {
        query = "insert into user (username, password, phoneNumber, userImageLink, profileDesc) values (?,?,?,?,?)";
        preQuery = con.prepareStatement(query);
        preQuery.setString(1, user.getUsername());
        preQuery.setString(2, user.getPassword());
        preQuery.setString(3, user.getPhoneNumber());
        preQuery.setString(4, user.getUserImageLink());
        preQuery.setString(5, user.getProfileDesc());
        preQuery.executeUpdate();
    }
    public int loginValidation(User u)throws SQLException{
        if (u.getUsername().equals("") || u.getPassword().equals(""))
            return 0;   // empty fields
        if(!searchUser(u.getUsername()))
            return 2; // user not found
        query = "select count(*) from user where username = ? and password = ?";
        preQuery = con.prepareStatement(query);
        preQuery.setString(1, u.getUsername());
        preQuery.setString(2, u.getPassword());
        ResultSet result = preQuery.executeQuery();
        result.next();
        if (result.getInt(1) == 1)
            return 1;       // every thing is ok
        else
            return 3;       //pass is wrong
    }
    public int userDataValidation(User user) throws SQLException {
        if (user.getUsername().equals("") || user.getPassword().equals("") || user.getPhoneNumber().equals(""))
            return -1;
        // check every attribute if it exists or not in the database by count the number of elements that matches
        //  the user input and return a value for the caller function to decide
        if(!utilities.checkPhoneNum(user.getPhoneNumber()))
            return 3;
        //check  username if it exists by count function as if it was zero then it does not exist otherwise it exists
        query = "select count(username) from user where username = ?";
        preQuery = con.prepareStatement(query);
        preQuery.setString(1, user.getUsername());
        if (countChecker() == 1)
            return 1;

        //check phoneNumber same as above
        query = "select count(phoneNumber) from user where phoneNumber = ?";
        preQuery = con.prepareStatement(query);
        preQuery.setString(1, user.getPhoneNumber());
        if (countChecker() == 1)
            return 2;
        return 0;
    }
    public User getUserByPhoneNumber(String phoneNum) throws SQLException {
        query = "select id, username, phoneNumber, password, profileDesc, profileVisibility,userImageLink from user where phoneNumber = ?";
        preQuery = con.prepareStatement(query);
        preQuery.setString(1, phoneNum);
        ResultSet tmp = preQuery.executeQuery();
        if(tmp.next())
            return new User(tmp.getInt(1), tmp.getString(2), tmp.getString(3), tmp.getString(4), tmp.getString(5), tmp.getBoolean(6),tmp.getString(7));
        return null;
    }
    public User getUser(int userId) throws SQLException {
        query = "select username, password, phoneNumber, profileDesc, profileVisibility,userImageLink from user where id = ?";
        preQuery = con.prepareStatement(query);
        preQuery.setInt(1, userId);
        ResultSet tmp = preQuery.executeQuery();
        tmp.next();
        return new User(userId, tmp.getString(1), tmp.getString(3), tmp.getString(2), tmp.getString(4), tmp.getBoolean(5),tmp.getString(6));
    }
    private boolean searchUser(String userName) throws SQLException {
        //mody's work
        //check for username as if it exists & pass is wrong return alert says pass wrong
        query = "select count(*) from user where username = ?";
        preQuery = con.prepareStatement(query);
        preQuery.setString(1, userName);
        ResultSet result = preQuery.executeQuery();
        result.next();
        if(result.getInt(1) == 1)
            return true;            //user name exists
        return false;               // user not found
        //mody's work
    }
    public String getUserName(int id) throws SQLException {
        String query = "select username from user where id = ?";
        PreparedStatement preQuery = con.prepareStatement(query);
        preQuery.setInt(1, id);
        ResultSet result = preQuery.executeQuery();
        result.next();
        return result.getString(1);
    }
    public int getUserIdFromUsername(String username) throws SQLException{
        query = "select id from user where username = ?";
        preQuery = con.prepareStatement(query);
        preQuery.setString(1, username);
        ResultSet result = preQuery.executeQuery();
        result.next();
        return result.getInt(1);
    }
    public int getLastUserId() throws SQLException{
        query = "select * from user order by id desc";
        preQuery = con.prepareStatement(query);
        ResultSet r = preQuery.executeQuery();
        if (r.next())
            return r.getInt("id");
        return 0;
    }
    public String getFriendName(int userId, int friendId) throws SQLException{
        // return is ("friendName" || null || "0")
        query = "select friendName from userConnection where userId = ? and friendId= ?";
        preQuery = con.prepareStatement(query);
        preQuery.setInt(1, userId);
        preQuery.setInt(2, friendId);
        ResultSet userConnectionResult = preQuery.executeQuery();
        if(userConnectionResult.next())
            return userConnectionResult.getString("friendName");
        return "0";     // there is No any connection between them
    }
    public int countChecker() throws SQLException {
        ResultSet result = preQuery.executeQuery();
        result.next();
        return result.getInt(1);
    }
    public void resetPassword(int userId, String newPassword) throws SQLException{
        query = "update user set password = ? where id = ?";
        preQuery = con.prepareStatement(query);
        preQuery.setString(1, newPassword);
        preQuery.setInt(2, userId);
        preQuery.executeUpdate();
    }
    public void resetUserDesc(int userId, String profileDesc) throws SQLException{
        query = "update user set profileDesc = ? where id = ?";
        preQuery = con.prepareStatement(query);
        preQuery.setString(1, profileDesc);
        preQuery.setInt(2, userId);
        preQuery.executeUpdate();
    }
    public void resetUserProfileVisibility(int userId, boolean profileVisibility) throws SQLException{
        query = "update user set profileVisibility = ? where id = ?";
        preQuery = con.prepareStatement(query);
        preQuery.setBoolean(1, profileVisibility);
        preQuery.setInt(2, userId);
        preQuery.executeUpdate();
    }
    public void resetUserImage(int userId, String imageLink) throws SQLException{
        query = "update user set userImageLink = ? where id = ?";
        preQuery = con.prepareStatement(query);
        preQuery.setString(1, imageLink);
        preQuery.setInt(2, userId);
        preQuery.executeUpdate();
    }
    public int addConnection(int currentUserId, int friendId, String friendName) throws SQLException{
    // check before creating the chatroom if the other friend already added him
    if (getFriendName(currentUserId, friendId) == null){
        query = "update userConnection set friendName = ? where userId = ? and friendId = ?";
        preQuery = con.prepareStatement(query);
        preQuery.setString(1, friendName);
        preQuery.setInt(2, currentUserId);
        preQuery.setInt(3, friendId);
        preQuery.executeUpdate();
        return 2;
    }
    else {
        query = "insert into userConnection(userId, friendId, friendName) values(?, ?, ?)";
        preQuery = con.prepareStatement(query);
        preQuery.setInt(1, currentUserId);
        preQuery.setInt(2, friendId);
        preQuery.setString(3, friendName);
        preQuery.executeUpdate();
        query = "insert into userConnection(userId, friendId) values(?, ?)";
        preQuery = con.prepareStatement(query);
        preQuery.setInt(1, friendId);
        preQuery.setInt(2, currentUserId);
        preQuery.executeUpdate();
        // create a chat room between both of them and call it a const name 'private'
        // note that the group name is not a primary key in the database
        query = "insert into chatRoom (name) values ('private')";
        preQuery = con.prepareStatement(query);
        preQuery.executeUpdate();
        //get the last record id which is equals to the count of records in the Database because the data is sorted by the id
        int lastCreatedChatId = getLastRowChatroom();
        query = "insert into userJoinChat(userId, chatId) values (? , ?)";
        preQuery = con.prepareStatement(query);
        preQuery.setInt(1, currentUserId);
        preQuery.setInt(2, lastCreatedChatId);
        preQuery.executeUpdate();
        query = "insert into userJoinChat(userId, chatId) values(?, ?)";
        preQuery = con.prepareStatement(query);
        preQuery.setInt(1, friendId);
        preQuery.setInt(2, lastCreatedChatId);
        preQuery.executeUpdate();
        return 1;
    }
}
    public void addStory(int userId, String storyText, boolean isImage) throws SQLException{
        query = "insert into story (storyUploaderId, Text,isImage) values(?,?,?)";
        preQuery = con.prepareStatement(query);
        preQuery.setInt(1, userId);
        preQuery.setString(2, storyText);
        preQuery.setBoolean(3, isImage);
        preQuery.executeUpdate();
    }
    public int createGroup(String groupName,List<User> list, String imageLink)throws SQLException {
        // we have private keyword to specify our 2 members only private chat
        // for that the user cannot select "private" for the name of the group
        //create the user group
        query="insert into chatroom(name, chatroomImageLink) values(?,?)";
        preQuery = con.prepareStatement(query);
        preQuery.setString(1,groupName);
        preQuery.setString(2, imageLink);
        preQuery.executeUpdate();
        System.out.println("you created the group of "+groupName);
        int lastCreatedGroupId = getLastRowChatroom();
        //iterate over the user ArrayList and join them all to our created group
        for(int i=0;i<list.size();i++){
            query="insert into userJoinChat values(?,?, default, curdate(), curtime(), now())";
            preQuery = con.prepareStatement(query);
            preQuery.setInt(1,lastCreatedGroupId);
            preQuery.setInt(2,list.get(i).getId());
            preQuery.executeUpdate();
            System.out.println("you added "+list.get(i).getUsername());
        }
        return lastCreatedGroupId;
    }
    public void checkStoryDate() throws SQLException{
        query = "DELETE FROM story WHERE storyDateTimeAdded < DATE_SUB(NOW(), INTERVAL 1 DAY)";
        preQuery = con.prepareStatement(query);
        preQuery.executeUpdate();
    }
    public Message getLastMessage(int chatId) throws SQLException{
        query = "select * from message where chatId = ? order by date desc, time desc";
        preQuery = con.prepareStatement(query);
        preQuery.setInt(1, chatId);
        ResultSet result = preQuery.executeQuery();
        if (result.next()){
            return new Message(result.getInt("id"), result.getInt("senderId"), result.getInt("chatId"), result.getString("messageText"),
                    result.getDate("date").toString(), result.getTime("time").toString(), result.getBoolean("seenStatus"),
                    result.getTimestamp("dateTime"));
        }
        return null;
    }
    public List<ChatRoom> expandConnectionChats(int userId) throws SQLException{
        // for a specific user get all rooms which he participated in
        // then save its information in an ArrayList
        query = "select chatId from userJoinChat where userId = ?";
        preQuery = con.prepareStatement(query);
        preQuery.setInt(1, userId);
        ResultSet result = preQuery.executeQuery();
        LinkedList<ChatRoom> chatRooms = new LinkedList<>();
        // using the chatRoom id of the last query and using it for getting all chat info
        // with inner loop query in the chatRoom relation
        while (result.next()){
            query = "select id, name, dateOfGroupCreation, timeOfGroupCreation, chatroomImageLink from chatRoom where id = ?";
            preQuery = con.prepareStatement(query);
            preQuery.setInt(1, result.getInt(1));
            ResultSet tmpResult = preQuery.executeQuery();
            tmpResult.next();
            ChatRoom chat = new ChatRoom(tmpResult.getInt(1), tmpResult.getString(2),tmpResult.getDate(3).toString(),
                    tmpResult.getTime(4).toString(),tmpResult.getString(5));
            chat.setUserList(showChatInfo(chat.getId(), userId));
            chat.setMessageList(expandMessages(chat.getId(), userId));
            Message lastMessage = getLastMessage(chat.getId());
            if (lastMessage != null)
                chat.setLastMessageSent(getLastMessage(chat.getId()));
            else
                chat.setLastMessageSent(new Message());
            chat.setNumberOfUnreadMessagesForCurrentUser(0);    // use her number of notifications
            if (chat.getName().equals("private")){
                int friendId = 0;
                for (User u : chat.getUserList()){
                    if (u.getId() != userId){
                        friendId = u.getId();
                        break;
                    }
                }
                chat.setName(getFriendName(userId, friendId));
            }
            chatRooms.add(chat);
        }
        Collections.sort(chatRooms);
       /* for (ChatRoom c: chatRooms){
            System.out.println(c.getLastMessageSent().getDateTime() + " " + c.getLastMessageSent().getMessageText());
        }*/
        return chatRooms;
    }
    public LinkedList<Story> getStoryList(int userId) throws SQLException{
        LinkedList<Story> storyList = new LinkedList<>();
        checkStoryDate();
        query = "select * from story where storyUploaderId = ?";
        preQuery = con.prepareStatement(query);
        preQuery.setInt(1,userId);
        ResultSet storyResult = preQuery.executeQuery();
        while (storyResult.next())
            storyList.add(new Story(storyResult.getInt("storyUploaderId"), storyResult.getString("Text"), storyResult.getDate("storyDateUploaded"),
                    storyResult.getTime("storyTimeUploaded"),storyResult.getBoolean("isImage")));
        for (Story f:storyList) {
            System.out.println(f.getStoryText());
            System.out.println(f.getStoryUploadedTime());
            System.out.println(f.getStoryUploaderId());
        }
        return storyList;
    }
    public LinkedList <User> getFriendList(int userId) throws SQLException{
    LinkedList<User>friendList = new LinkedList<>();
    query = "select userID, friendId, friendName from userConnection where userId = ?";
    preQuery = con.prepareStatement(query);
    preQuery.setInt(1, userId);
    ResultSet userConnectionResult = preQuery.executeQuery();
    while (userConnectionResult.next()){
        if (userConnectionResult.getString("friendName") != null)
            friendList.addLast(getUser(userConnectionResult.getInt("friendId")));
    }
    return friendList;
}
    public LinkedList<Message> expandMessages(int chatId, int currentUserId) throws SQLException {
        LinkedList<Message>chatMessages = new LinkedList<>();
       // ArrayList<User> allUsers = this.showChatInfo(chatId, currentUserId);
        // make a query to get all the messages of a specific chat from database sorted by the older ones first using
        // date message sent first then time to order our data
        query = "select senderId, chatId, messageText, date, time, seenStatus, id, datetime from message where chatId = ? order by date asc, time asc";
        //to make the pointer of resultSet go forward and backward in the dataSet we have to make this next line
        preQuery = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        preQuery.setInt(1, chatId);
        ResultSet result = preQuery.executeQuery();
        System.out.println("____________________________________");
        while (result.next()) {
            //detect a cycle
            boolean innerLoopCheckInfinity = false;
            //take the first message date so that the message could be printed with ascending order of date
            String testConstDate = result.getString(4);
            //for all messages have the same date print them all
            System.out.println("\t\t\t[[[" + testConstDate + "]]]\n\n");
            while (testConstDate.equals(result.getString(4))) {
                innerLoopCheckInfinity = true;
                // current opened user message will be printed on the left half of the screen
                // and his friends will be on the right half
                // if the i'th user is our user id that is passed as a parameter
                if (result.getInt(1) == currentUserId){
                    System.out.println(getUserName(currentUserId) + " (YOU) " +  "\n" + result.getString("messageText") +
                            "\n[" + result.getString("time") + "]\n");

                    Message m = new Message(result.getInt("id"),result.getInt(1), result.getInt(2)
                            ,result.getString(3), result.getDate(4).toString(), result.getTime(5).toString(),
                            result.getBoolean(6), result.getTimestamp(8));
                    chatMessages.add(m);
                }
                // if the i'th user is not our user id that is passed as a parameter
                else{
                    System.out.println("\t\t\t\t\t" + getUserName(result.getInt("senderId")) + "\n\t\t\t\t\t" + result.getString("messageText") + "\n\t\t\t\t\t["
                            + result.getString("time") + "]\n");
                    chatMessages.add(new Message(result.getInt("id"),result.getInt(1), result.getInt(2)
                            ,result.getString(3), result.getDate(4).toString(), result.getTime(5).toString(),
                            result.getBoolean(6), result.getTimestamp(8)));
                }
                if (!result.next())
                    break;
            }
            System.out.println("____________________________________");
            // the previous inner while loop could make a cycle if it's condition was not true and the resultSet pointer will go back and forward
            //in the same data and check the condition
            if (innerLoopCheckInfinity)
                result.previous();
        }
        return chatMessages;
    }
    public boolean sendMessage(int currentUserId,int chatId, String msg)throws SQLException {
        query = "insert into message(senderId,chatId,messageText) values(?,?,?)";
        preQuery = con.prepareStatement(query);
        preQuery.setInt(1,currentUserId);
        preQuery.setInt(2,chatId);
        preQuery.setString(3,msg);
        preQuery.executeUpdate();
        return true;
    }
    public void deleteMessage(/*int userId, int currentUserOpenedChatId, */int messageId) throws SQLException{
        query = "delete from message where id = ?";
        preQuery = con.prepareStatement(query);
//        preQuery.setInt(1, userId);
        //preQuery.setInt(2, currentUserOpenedChatId);
        preQuery.setInt(1, messageId);
        preQuery.executeUpdate();
    }
    public int getLastRowChatroom()throws SQLException{
        query = "select * from chatroom order by id desc";
        preQuery = con.prepareStatement(query);
        ResultSet r = preQuery.executeQuery();
        r.next();
        return r.getInt("id");

    }
    public ArrayList<User> showChatInfo(int chatId, int currentUserId) throws SQLException {
        ChatRoom chat;
        ArrayList<User>userList = new ArrayList<>();
        query = "select * from chatRoom where id = ?";
        preQuery = con.prepareStatement(query);
        preQuery.setInt(1, chatId);
        ResultSet result = preQuery.executeQuery();
        result.next();
        chat = new ChatRoom(chatId, result.getString(2));
        query = "select userId, chatId, lastDateChatOpened, lastTimeChatOpened, isBlocked from userJoinChat where chatId = ?";
        preQuery = con.prepareStatement(query);
        preQuery.setInt(1, chatId);
        result = preQuery.executeQuery();
        User u = new User();
        System.out.println("\t\t" + chat.getName() + "\n___________________________"+  "\nGroup Members: \n" +
                "___________________________");
        while (result.next()) {
            // in the database if the user is removed from group
            if (result.getBoolean(5))
                continue;
            // the user who opens the application must see the usernames by what he calls them in userConnection table and
            // if he did not call them then it will show their original names
            query = "select friendName from userConnection where userId = ? and friendId = ?";
            preQuery = con.prepareStatement(query);
            String lastDateChatOpened = result.getString(3);
            String lastTimeChatOpened = result.getString(4);
            if (currentUserId == result.getInt(1)) {
                chat.getUserList().add(getUser(result.getInt(1)));
                System.out.println("YOU");
                if (lastDateChatOpened == null){
                    System.out.println("Not opened Yet" + "\n");
                    /*u.setLastDateOpened(null);
                    u.setLastTimeOpened(null);*/
                }
                else{
                    System.out.println(result.getString(3) + " " +result.getString(4) + "\n");
                    u.setLastDateOpened(result.getString(3));
                    u.setLastTimeOpened(result.getString(4));
                }
                User tmpUser = getUser(result.getInt(1));
                userList.add(tmpUser);
                continue;
            }
            preQuery.setInt(1, currentUserId);
            preQuery.setInt(2, result.getInt(1));
            ResultSet userConnectionRelation = preQuery.executeQuery();
            //the next if else condition is described as if the query returned records (at most one record)
            // then he is a friend of you ,or he  added you, but you did  not yet so if the friendName is null we will get the
            // original username for the i'th user we iterate for and this the else statement below
            if (userConnectionRelation.next()) {
                u = getUser(result.getInt(1));
                if (userConnectionRelation.getString(1) != null)
                    u.setUsername(userConnectionRelation.getString(1));
                else
                    u.setUsername(getUserName(result.getInt(1)));
                chat.getUserList().add(u);
                if (lastDateChatOpened == null) {
                    System.out.println( u.getUsername() + "\n" + "Not opened Yet" + "\n");
                    /*u.setLastDateOpened(null);
                    u.setLastTimeOpened(null);*/
                    userList.add(u);
                }
                // descriped above
                else{
                    System.out.println( u.getUsername() + "\n" + result.getString(3) + " " +result.getString(4) + "\n");
                    u.setLastDateOpened(result.getString(3));
                    u.setLastTimeOpened(result.getString(4));
                    userList.add(u);
                }
            }
            else {
                u = getUser(result.getInt(1));
                chat.getUserList().add(u);
                if (lastDateChatOpened == null) {
                    System.out.println(u.getUsername() + "\n" + "Not opened Yet" + "\n");
                   /* u.setLastDateOpened(null);
                    u.setLastTimeOpened(null);*/
                    userList.add(u);
                }
                else{
                    System.out.println(u.getUsername() + "\n" + result.getString(3) + " " + result.getString(4) + "\n");
                    u.setLastDateOpened(result.getString(3));
                    u.setLastTimeOpened(result.getString(4));
                    userList.add(u);
                }
            }
        }
        for (User user : userList){
            user.setCurrentChatId(chatId);
        }
        return userList;
    }
    public boolean isPrivate(int chatId) throws SQLException {
        query = "select * from chatroom where id = ?";
        preQuery = con.prepareStatement(query);
        preQuery.setInt(1,chatId);
        ResultSet r = preQuery.executeQuery();
        r.next();
        return r.getString("name").equals("private");
    }
    public void openChat(User user, int currentSelectedChat) throws SQLException{
        query = "update userJoinChat set lastDateChatOpened = curdate(), lastTimeChatOpened = curtime()," +
                "dateTimeUserOpenedThisChat = now() where chatId = ? and userId = ?";
        preQuery = con.prepareStatement(query);
        preQuery.setInt(1, currentSelectedChat);
        preQuery.setInt(2, user.getId());
        preQuery.executeUpdate();
        makeUserSeeMessage(user.getId(), currentSelectedChat);
    }
    public String lastOpenChatDateTime(int chatId, int userId)throws SQLException{
        query = "select lastDateChatOpened, lastTimeChatOpened from userJoinChat where chatId = ? and userId = ?";
        preQuery = con.prepareStatement(query);
        preQuery.setInt(1, chatId);
        preQuery.setInt(2,userId);
        ResultSet userJoinChatResult2 = preQuery.executeQuery();
        userJoinChatResult2.next();
        if (userJoinChatResult2.getDate("lastDateChatOpened") != null)
            return userJoinChatResult2.getDate("lastDateChatOpened") + " " + userJoinChatResult2.getTime("lastTimeChatOpened");
        return null;
    }

    public int numberOfChatMembers(int chatId) throws SQLException{
        query = "select count(*) from userJoinChat where chatId = ?";
        preQuery = con.prepareStatement(query);
        preQuery.setInt(1, chatId);
        ResultSet userJoinChatResult = preQuery.executeQuery();
        userJoinChatResult.next();
        return userJoinChatResult.getInt(1);
    }
    public int numberOfMessageViewers(int messageId)throws SQLException{
        query = "select count(*) from messageViewer where messageId = ?";
        preQuery = con.prepareStatement(query);
        preQuery.setInt(1, messageId);
        ResultSet messageViewerCount = preQuery.executeQuery();
        messageViewerCount.next();
        return messageViewerCount.getInt(1);
    }
    public void setMessageSeenStatus(int chatId, int messageId) throws SQLException{
        int numberOfChatMembers = numberOfChatMembers(chatId);
        int messageViewersCount = numberOfMessageViewers(messageId);
        if (numberOfChatMembers-1 == messageViewersCount){
            query = "update message set seenStatus = true where id = ?";
            preQuery = con.prepareStatement(query);
            preQuery.setInt(1, messageId);
            preQuery.executeUpdate();
        }
    }
    public void setAllMessagesSeenStatus(int chatId) throws SQLException{
        query = "select id, seenStatus from message where chatId = ? order by dateTime desc";
        preQuery = con.prepareStatement(query);
        preQuery.setInt(1, chatId);
        ResultSet messageSetAllMessagesSeenStatus = preQuery.executeQuery();
        while (messageSetAllMessagesSeenStatus.next() && !messageSetAllMessagesSeenStatus.getBoolean("seenStatus"))
            setMessageSeenStatus(chatId, messageSetAllMessagesSeenStatus.getInt("id"));
    }
    public boolean isSeenByUser(int userId, int messageId) throws SQLException {
        query = "select * from messageViewer where userId = ? and messageId = ?";
        preQuery = con.prepareStatement(query);
        preQuery.setInt(1,userId);
        preQuery.setInt(2,messageId);
        ResultSet messageViewerResult = preQuery.executeQuery();
        if (messageViewerResult.next())
            return true;
        return false;
    }
    public void makeUserSeeMessage(int userId, int chatId) throws SQLException{
        query = "select id from message where senderId <> ? and chatId = ? order by dateTime desc";
        preQuery = con.prepareStatement(query);
        preQuery.setInt(1, userId);
        preQuery.setInt(2, chatId);
        ResultSet messageResult = preQuery.executeQuery();
        while (messageResult.next()){
            int messageId = messageResult.getInt("id");
            if (isSeenByUser(userId, messageId))
                break;
            query = "insert into messageViewer values(?,?)";
            preQuery = con.prepareStatement(query);
            preQuery.setInt(1, userId);
            preQuery.setInt(2, messageId);
            preQuery.executeUpdate();
        }
    }
    public int unseenMessage(int userId, int chatId) throws SQLException{
        query = "select id from message where senderId <> ? and chatId = ? order by dateTime desc";
        preQuery = con.prepareStatement(query);
        preQuery.setInt(1, userId);
        preQuery.setInt(2, chatId);
        ResultSet unseenMessageResult = preQuery.executeQuery();
        int unseenCounter = 0;
        while (unseenMessageResult.next() && !isSeenByUser(userId, unseenMessageResult.getInt("id")))
            unseenCounter++;
        return unseenCounter;
    }
    public boolean getSeenStatus(int messageId) throws SQLException{
        query = "select seenStatus from message where id = ?";
        preQuery = con.prepareStatement(query);
        preQuery.setInt(1, messageId);
        ResultSet seenStatusResult = preQuery.executeQuery();
        seenStatusResult.next();
        return seenStatusResult.getBoolean("seenStatus");
    }
}
