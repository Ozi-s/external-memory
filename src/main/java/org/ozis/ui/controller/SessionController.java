package org.ozis.ui.controller;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.ozis.dao.WorkSessionDao;
import org.ozis.entity.Project;
import org.ozis.entity.Task;
import org.ozis.entity.WorkSession;
import org.ozis.ui.UiManager;
import org.ozis.ui.window.EndSessionWindow;

public class SessionController {

    private final UiManager uiManager;
    private final WorkSessionDao workSessionDao = new WorkSessionDao();

    public SessionController(UiManager uiManager) {
        this.uiManager = uiManager;
    }

    // ==== Чтение ====

    public Optional<WorkSession> getActiveSession() {
        return workSessionDao.findActiveSession();
    }

    public List<WorkSession> getRecentSessions(Project project, int limit) {
        if (project == null) return List.of();
        return workSessionDao.findSesionByProject(project)
            .stream()
            .limit(limit)
            .toList();
    }

    // ==== Действия ====

    public void startSession(Project project, Task focus) {
        if (project == null) return;
        workSessionDao.startSession(project, focus);
        uiManager.refreshView("Context");
    }

    public void setFocus(Task task) {
        if (task == null) return;

        Optional<WorkSession> active = workSessionDao.findActiveSession();
        if (active.isEmpty()) return;

        WorkSession session = active.get();
        session.setFocus(task);
        workSessionDao.update(session);

        uiManager.refreshView("Context");
    }

    public void openEndSessionDialog(WorkSession session) {
        if (session == null) return;
        EndSessionWindow window = new EndSessionWindow(this, session);
        window.show();
    }

    public void endSession(WorkSession session, String result, String notFinished,
                           String next, String openQuestion) {
        if (session == null) return;

        session.setResult(result);
        session.setNotFinished(notFinished);
        session.setNext(next);
        session.setOpenQuestion(openQuestion);
        session.setEndTime(LocalTime.now());
        workSessionDao.update(session);

        uiManager.refreshView("Context");
    }
}
