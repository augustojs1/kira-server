<h1 align="center"> 
	Kira Server V1
</h1>

<h3 align="center"> 
	Non-Functional Requirements
</h3>

- [ ] Kira Server should be developed using the following technologies: Java 17, Spring Boot, MySQL and Docker.
- [ ] Timestamp properties in MySQL tables.
- [ ] REST API should use prefix: '/api/v1/'
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
- [ ] Admin board user should be able edit board information.
- [ ] Admin board user should be able to delete their boards.
- [ ] Admin Users should be able to invite users to a board.
- [ ] Admin Users should be able to remove a user from a board.
- [ ] Admin board should change role of other.
- [ ] Board members can list board members list.

### #Board Members
- [ ] Board members can assign themselves to tasks.

### #Invite

- [ ] Admin board user can invite other users to their board.
- [ ] Invited users can deny or accept the invite.
- [ ] After accepting an invite, user should be that board member.

### #Status

- [ ] Board admin can create board tasks status up to 8 different status.
- [ ] Board admin can delete a status if no tasks are using that status.
- [ ] Status should have a logical order of 1 up to 8.

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