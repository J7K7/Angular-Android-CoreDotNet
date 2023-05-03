using EHealthCare.App_Start;
using Newtonsoft.Json;
using System.Collections.Generic;
using System.Linq;
using System.Web.Mvc;

namespace EHealthCare.Areas.Hospital.Controllers
{
    public class DoctorController : BaseController
    {
        // GET: Hospital/Doctor
        public ActionResult Index()
        {
            if (!HospitalId.HasValue)
                return RedirectToAction("Login", "Home");

            List<User> users = GetDoctor(0, null);

            return View("Index", users);
        }

        [HttpPost]
        public ActionResult Index(int? Specialization, bool? Status)
        {
            if (!Specialization.HasValue)
                Specialization = 0;

            List<User> users = GetDoctor(Specialization.Value, Status);

            return View("Index", users);
        }

        private List<User> GetDoctor(int Specialization, bool? Status)
        {
            string response = GetAPICall("Users/GetDoctorsByHospital?HospitalId=" + HospitalId + "&DoctorId=0&SpecializationId=" + Specialization + "&Status=" + Status);
            List<User> users = JsonConvert.DeserializeObject<List<User>>(response);

            response = GetAPICall("Specializations/Get");
            List<Specialization> specializations = JsonConvert.DeserializeObject<List<Specialization>>(response);

            List<SelectListItem> specializationsItem = specializations.Select(e => new SelectListItem { Value = e.Id.ToString(), Text = e.Name, Selected = (Specialization == e.Id ? true : false) }).ToList();
            ViewData["SpecializationItem"] = specializationsItem;

            List<SelectListItem> statusItem = new List<SelectListItem>();
            statusItem.Add(new SelectListItem { Text = "Active", Value = "true", Selected = (Status == true ? true : false) });
            statusItem.Add(new SelectListItem { Text = "Deactive", Value = "false", Selected = (Status == false ? true : false) });

            ViewData["StatusItem"] = statusItem;
            ViewBag.Status = Status;

            return users;
        }

        public ActionResult Detail(int Id)
        {
            if (!HospitalId.HasValue)
                return RedirectToAction("Login", "Home");

            string response = GetAPICall("Users/GetDoctorsByHospital?HospitalId=" + HospitalId + "&DoctorId=" + Id);
            List<User> users = JsonConvert.DeserializeObject<List<User>>(response);

            response = GetAPICall("DoctorSchedules/GetByDoctorByHospitalId?HospitalId=" + HospitalId + "&DoctorId=" + Id);
            List<DoctorSchedule> schedule = JsonConvert.DeserializeObject<List<DoctorSchedule>>(response);

            response = GetAPICall("Users/GetPatientsByHospital?HospitalId=" + HospitalId + "&DoctorId=" + Id);
            List<User> patients = JsonConvert.DeserializeObject<List<User>>(response);

            response = GetAPICall("HospitalDoctors/GetByHospitalDoctor?HospitalId=" + HospitalId + "&DoctorId=" + Id);
            List<HospitalDoctor> hospitalDoctors = JsonConvert.DeserializeObject<List<HospitalDoctor>>(response);

            return View((users.FirstOrDefault(), schedule, hospitalDoctors, patients));
        }

        public ActionResult UpdateStatus(int Id, bool Status, int DoctorId)
        {
            string response = GetAPICall("HospitalDoctors/PutStatus?Id=" + Id + "&Status=" + Status);
            string message = JsonConvert.DeserializeObject<string>(response);

            if(message == "Updated...!!!")
            {
                TempData["Type"] = "success";
                TempData["Title"] = "Success";
                TempData["Message"] = message;
            }

            return RedirectToAction("Detail", "Doctor", new { Id = DoctorId });
        }
    }
}