export interface UserSchedule {
    Id?: number;
    HospitalDoctorId?: number;
    HospitalName?: string;
    ScheduleDate?: Date;
    MorningStart?: string;
    MorningEnd?: string;
    EveningStart?: string;
    EveningEnd?: string;
    IsEmergency?: Boolean;
}
