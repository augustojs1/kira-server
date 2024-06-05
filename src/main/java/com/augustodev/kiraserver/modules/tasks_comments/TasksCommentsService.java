package com.augustodev.kiraserver.modules.tasks_comments;

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

@RequiredArgsConstructor
@Service
public class TasksCommentsService {
    private final TasksCommentsRepository tasksCommentsRepository;
    private final BoardService boardService;
    private final TasksService tasksService;
    private final TasksCommentsMapper tasksCommentsMapper;



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
}
