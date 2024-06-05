package com.augustodev.kiraserver.modules.tasks_comments;

import com.augustodev.kiraserver.common.exceptions.ResourceNotFoundException;
import com.augustodev.kiraserver.common.exceptions.UnauthorizedException;
import com.augustodev.kiraserver.modules.boards.BoardService;
import com.augustodev.kiraserver.modules.tasks.TasksService;
import com.augustodev.kiraserver.modules.tasks.entities.Task;
import com.augustodev.kiraserver.modules.tasks_comments.dtos.request.CreateTaskCommentDto;
import com.augustodev.kiraserver.modules.tasks_comments.dtos.response.TaskCommentSlimDto;
import com.augustodev.kiraserver.modules.tasks_comments.entities.TaskComment;
import com.augustodev.kiraserver.modules.tasks_comments.mapper.TasksCommentsMapper;
import com.augustodev.kiraserver.modules.users.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TasksCommentsService {
    private final TasksCommentsRepository tasksCommentsRepository;
    private final BoardService boardService;
    private final TasksService tasksService;
    private final TasksCommentsMapper tasksCommentsMapper;

    private TaskComment findByIdElseThrow(Integer id) {
        Optional<TaskComment> taskComment = this.tasksCommentsRepository.findById(id);

        if (taskComment.isEmpty()) {
            throw  new ResourceNotFoundException("Task comment with this id not found!");
        }

        return  taskComment.get();
    }

    public void isUserCommentOwner(TaskComment taskComment, User user) {
        if (!Objects.equals(taskComment.getUser().getId(), user.getId())) {
            throw new UnauthorizedException("User not authorized to perform this action!");
        }
    }
    public TaskCommentSlimDto create(CreateTaskCommentDto createTaskCommentDto, User user, Integer taskId) {
        Task task = this.tasksService.findTaskByIdElseThrow(taskId);

        this.boardService.findUserAssociatedBoardElseThrow(task.getBoard().getId(), user.getId());

        TaskComment taskComment = TaskComment.builder()
                        .task(task)
                        .user(user)
                        .content(createTaskCommentDto.getContent())
                        .build();

        TaskComment savedTaskComment = this.tasksCommentsRepository.save(taskComment);

        return this.tasksCommentsMapper.map(savedTaskComment);
    }

    public TaskCommentSlimDto update(CreateTaskCommentDto createTaskCommentDto, User user, Integer taskCommentId) {
        TaskComment taskComment = this.findByIdElseThrow(taskCommentId);

        this.isUserCommentOwner(taskComment, user);

        taskComment.setContent(createTaskCommentDto.getContent());

        TaskComment updatedTaskComment = this.tasksCommentsRepository.save(taskComment);

        return this.tasksCommentsMapper.map(updatedTaskComment);
    }

    public void delete(User user, Integer taskCommentId) {
        TaskComment taskComment = this.findByIdElseThrow(taskCommentId);

        this.isUserCommentOwner(taskComment, user);

        this.tasksCommentsRepository.delete(taskComment);
    }
}
