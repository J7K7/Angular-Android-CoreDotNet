using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Cors;
using EHealthCare.App_Start;

namespace EHealthCare.Controllers
{
    [EnableCors(origins: "*", headers: "*", methods: "*")]
    public class DoctorSchedulesController : BaseController
    {
        // GET: api/DoctorSchedules/Get?HospitalId=5&DoctorId=6
        [HttpGet]
        public async Task<IHttpActionResult> Get(int HospitalId, int DoctorId)
        {
            List<DoctorSchedule> doctorSchedules = await db.DoctorSchedules.ToListAsync();
            doctorSchedules = doctorSchedules.Where(e => (DoctorId > 0 || e.HospitalDoctor.DoctorSpecialization.DoctorId == DoctorId)
                && (HospitalId > 0 || e.HospitalDoctor.HospitalId == HospitalId)).ToList();

            return Ok(doctorSchedules);
        }


        // GET: api/DoctorSchedules/GetById/5
        [HttpGet]
        public async Task<IHttpActionResult> GetById(int Id)
        {
            DoctorSchedule doctorSchedule = await db.DoctorSchedules.FindAsync(Id);
            return Ok(doctorSchedule);
        }


        // GET: api/DoctorSchedules/GetByDoctorByHospitalId/5
        [HttpGet]
        public async Task<IHttpActionResult> GetByDoctorByHospitalId(int HospitalId, int DoctorId)
        {
            List<DoctorSchedule> schedules =
                (from doctorSchedules in db.DoctorSchedules.ToList()
                 join hospital in db.HospitalDoctors on doctorSchedules.HospitalDoctorId equals hospital.Id
                 join specialization in db.DoctorSpecializations on hospital.DoctorSpecializationId equals specialization.Id
                 where hospital.HospitalId == HospitalId && specialization.DoctorId == DoctorId
                 select new DoctorSchedule
                 {
                     Id = doctorSchedules.Id,
                     ScheduleDate = doctorSchedules.ScheduleDate,
                     MorningStart = doctorSchedules.MorningStart,
                     MorningEnd = doctorSchedules.MorningEnd,
                     EveningStart = doctorSchedules.EveningStart,
                     EveningEnd = doctorSchedules.EveningEnd,
                     IsEmergency = doctorSchedules.IsEmergency,
                     HospitalDoctorId = doctorSchedules.HospitalDoctorId
                 }).ToList();

            return Ok(schedules);
        }


        [HttpGet]
        public async Task<IHttpActionResult> GetHospitalsByDoctor(int DoctorId)
        {
            List<DoctorSchedule> schedules =
                (from doctorSchedules in db.DoctorSchedules.ToList()
                 where doctorSchedules.HospitalDoctor.DoctorSpecialization.DoctorId == DoctorId
                 select new DoctorSchedule
                 {
                     Id = doctorSchedules.Id,
                     HospitalName = doctorSchedules.HospitalDoctor.DoctorSpecialization.User.FirstName + " " + doctorSchedules.HospitalDoctor.DoctorSpecialization.User.LastName,
                     ScheduleDate = doctorSchedules.ScheduleDate,
                     MorningStart = doctorSchedules.MorningStart,
                     MorningEnd = doctorSchedules.MorningEnd,
                     EveningStart = doctorSchedules.EveningStart,
                     EveningEnd = doctorSchedules.EveningEnd,
                     IsEmergency = doctorSchedules.IsEmergency,
                     HospitalDoctorId = doctorSchedules.HospitalDoctorId
                 }).ToList();

            return Ok(schedules);
        }


        // POST: api/DoctorSchedules/Post
        [HttpPost]
        public async Task<IHttpActionResult> Post(DoctorSchedule doctorSchedule)
        {
            db.DoctorSchedules.Add(doctorSchedule);
            await db.SaveChangesAsync();

            return Ok("Inserted...!!!");
        }


        // POST: api/DoctorSchedules/Put
        [HttpPost]
        public async Task<IHttpActionResult> Put(DoctorSchedule doctorSchedule)
        {
            db.Entry(doctorSchedule).State = EntityState.Modified;
            await db.SaveChangesAsync();

            return Ok("Updated...!!!");
        }


        // POST: api/DoctorSchedules/Delete/5
        [HttpPost]
        public async Task<IHttpActionResult> Delete(int id)
        {
            db.DoctorSchedules.Remove(await db.DoctorSchedules.FindAsync(id));
            await db.SaveChangesAsync();

            return Ok("Deleted...!!!");
        }
    }
}