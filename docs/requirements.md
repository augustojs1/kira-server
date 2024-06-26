<h1 align="center"> 
	Kira Server V1
</h1>

<h3 align="center"> 
	Non-Functional Requirements
</h3>

### V1
- [x] Kira Server should be developed using the following technologies: Java 17, Spring Boot, MySQL and Docker.
- [x] Timestamp properties in MySQL tables.
- [x] REST API should use prefix: '/api/v1/'
- [x] Should use JWT for authentication.
- [x] Docker container for the database.
- [x] Swagger OpenApi documentation.
- [x] Docker container for the application.

### V2
- [ ] Add Kafka container.
- [ ] Add notification-service microservice.
- [ ] Add email-service microservice.
- [ ] Add Unit and Integration tests.
- [ ] Deploy application to AWS.

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
- [x] Board members can assign themselves to tasks.

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
- [x] User should be able to see a list of status associated to board.
- [x] A board should not have more than 8 different status.
- [x] Board admin can delete a status if no tasks are using that status.
- [x] Create logic to user swap status position.

### #Tasks

- [x] Board members with admin permission should be able to create tasks.
- [x] Tasks should have title, description, status from other board members.
- [x] Users can be assigned to tasks.
- [x] Users can change task status.
- [x] List tasks assigned to a selected board member.
- [x] List a task by id and it's associated comments.
- [x] Board admin can delete a task.
- [x] Board members can list all tasks from a board.

### #Comments

- [x] Board members can add comments to board tasks.
- [x] Comment owner can delete their comments.
- [x] Comment owner can edit their comments.
- [x] Deleted tasks should delete related comments.

### #Users

- [x] Users can see their profile.
