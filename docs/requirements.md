<h1 align="center"> 
	Kira Server V1
</h1>

<h3 align="center"> 
	Non-Functional Requirements
</h3>

- [ ] Kira Server should be developed using the following technologies: Java 17, Spring Boot, MySQL and Docker.
- [x] Timestamp properties in MySQL tables.
- [x] REST API should use prefix: '/api/v1/'
- [ ] Should feature a seeder for data.
- [ ] Docker container for the database.
- [ ] Docker container for the application.

<h3 align="center"> 
	Functional Requirements
</h3>

### #Authentication

- [x] Users should be able to sign up.
- [x] Users should be able to sign in.

### #Boards

- [x] Users should be able to create a board.
- [x] User creating board should be automatically assigned to board admin.
- [x] Admin board user should be able to edit board information.
- [x] Admin board user should be able to delete their boards.
- [x] Admin Users should be able to invite users to a board.
- [x] Admin users should be able to remove a user from a board.
- [x] Admin board users should be able to change role of other board members.
- [x] Users should be able to fetch what boards they are in.
- [x] Board members can list board members list.

### #Board Members
- [ ] Board members can assign themselves to tasks.

### #Invite

- [x] Admin board user can invite other users to their board.
- [x] Users can fetch boards that he has been invited.
- [x] Invited users can deny an invitation.
- [x] Invited users can accept an invitation.
- [x] Users can fetch invitations sent by them.
- [x] Users can fetch their received invites.
- [x] After accepting an invitation, user should be that board member.

### #Status

- [x] Board admin can create board tasks status up to 8 different status.
- [x] Status should have a logical order of 1 up to 8.
- [ ] User should be able to see a list of status associated to board.
- [x] A board should not have more than 8 different status.
- [ ] Board admin can delete a status if no tasks are using that status.

### #Tasks

- [ ] Board members with admin permission should be able to create tasks.
- [ ] Tasks should have title, description, status from other board members.
- [ ] Users can be assigned to tasks.
- [ ] User assigned to task can change task status.
- [ ] Board admin can assignee other board members to a task.
- [ ] List tasks assigned to a selected board member.
- [ ] List tasks assigned to logged user.

### #Comments

- [ ] Board members can add comments to board tasks.
- [ ] Comment owner can delete or edit their comments.

### #Users

- [ ] Users can see their profile.
