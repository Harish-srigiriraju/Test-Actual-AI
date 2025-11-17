package com.petreach.appointmentscheduler.service;

import com.petreach.appointmentscheduler.entity.WorkingPlan;
import com.petreach.appointmentscheduler.model.TimePeroid;

public interface WorkingPlanService {
    void updateWorkingPlan(WorkingPlan workingPlan);

    void addBreakToWorkingPlan(TimePeroid breakToAdd, int planId, String dayOfWeek);

    void deleteBreakFromWorkingPlan(TimePeroid breakToDelete, int planId, String dayOfWeek);

    WorkingPlan getWorkingPlanByProviderId(int providerId);
}
