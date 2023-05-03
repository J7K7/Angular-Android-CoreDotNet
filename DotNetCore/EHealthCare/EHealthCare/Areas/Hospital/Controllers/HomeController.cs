using EHealthCare.App_Start;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Web;
using System.Web.Mvc;

namespace EHealthCare.Areas.Hospital.Controllers
{
    public class HomeController : BaseController
    {
        // GET: Hospital/Home
        public ActionResult Index()
        {
            if (!HospitalId.HasValue)
                return RedirectToAction("Login", "Home");

            string response = GetAPICall("UserImages/GetByUserId?UserId=" + HospitalId);
            List<UserImage> images = JsonConvert.DeserializeObject<List<UserImage>>(response);

            return View("Index", images);
        }


        #region Login


        public ActionResult Login()
        {
            if (HospitalId.HasValue)
                return RedirectToAction("Index", "Home");

            return View();
        }

        [HttpPost]
        public ActionResult Login(User user)
        {
            string response = PostAPICall("Users/Login", user);
            (string, User) message = JsonConvert.DeserializeObject<(string, User)>(response);

            if (message.Item1 == "Hospital")
            {
                Session["HospitalId"] = message.Item2.Id;
                Session["FirstName"] = message.Item2.FirstName;
                Session["LastName"] = message.Item2.LastName;
                Session["Email"] = message.Item2.Email;
                Session["MobileNo"] = message.Item2.MobileNo;
                if (message.Item2.UserImages.Count() > 0)
                    Session["ProfileImage"] = message.Item2.UserImages.FirstOrDefault().Name;

                return RedirectToAction("Index", "Home");
            }

            TempData["message"] = message.Item1;

            return View("Login", user);
        }


        #endregion Login


        #region Register


        public ActionResult Register()
        {
            if (HospitalId.HasValue)
                return RedirectToAction("Index", "Home");

            return View();
        }

        [HttpPost]
        public ActionResult Register(User user)
        {
            string response = PostAPICall("Users/RegisterHospital", user);
            (string, User) message = JsonConvert.DeserializeObject<(string, User)>(response);

            if (message.Item1 == "Register Successfully...!!!")
            {
                TempData["Type"] = "success";
                TempData["Title"] = "Success";
                TempData["Message"] = message.Item1;

                return RedirectToAction("Login", "Home");
            }

            TempData["Type"] = "error";
            TempData["Title"] = "Error";
            TempData["Message"] = message.Item1;

            return View("Register", user);
        }


        #endregion Register


        #region Profile


        public ActionResult Profile()
        {
            if (!HospitalId.HasValue)
                return RedirectToAction("Login", "Home");

            string response = GetAPICall("UsersAddress/GetByUser?UserId=" + HospitalId);
            List<UsersAddress> addresses = JsonConvert.DeserializeObject<List<UsersAddress>>(response);

            response = GetAPICall("UserImages/GetByUserId?UserId=" + HospitalId);
            List<UserImage> images = JsonConvert.DeserializeObject<List<UserImage>>(response);

            ViewData["Countries"] = GetCountry();

            return View("Profile", (addresses.FirstOrDefault(), images));
        }

        [HttpPost]
        public ActionResult PutProfileAddress(UsersAddress address)
        {
            string response;
            address.UserId = HospitalId.Value;

            if (address.Id == 0)
                response = PostAPICall("UsersAddress/Post", address);

            else response = PostAPICall("UsersAddress/Put", address);

            string message = JsonConvert.DeserializeObject<string>(response);

            TempData["Type"] = "success";
            TempData["Title"] = "Success";
            TempData["Message"] = message;

            return RedirectToAction("Profile", "Home");
        }

        [HttpPost]
        public ActionResult PutProfileImage(HttpPostedFileBase ProfileImage)
        {
            string response = PostAPIImage("UserImages/PostProfileImage?UserId=" + HospitalId, ProfileImage);

            (string, string) message = JsonConvert.DeserializeObject<(string, string)>(response);

            Session["ProfileImage"] = message.Item2;

            TempData["Type"] = "success";
            TempData["Title"] = "Success";
            TempData["Message"] = message.Item1;

            return RedirectToAction("Profile", "Home");

        }

        [HttpPost]
        public ActionResult PostImage(HttpPostedFileBase OtherImage)
        {
            string response = PostAPIImage("UserImages/Post?UserId=" + HospitalId, OtherImage);

            string message = JsonConvert.DeserializeObject<string>(response);

            TempData["Type"] = "success";
            TempData["Title"] = "Success";
            TempData["Message"] = message;

            return RedirectToAction("Profile", "Home");

        }

        public ActionResult DeleteImage(int Id)
        {
            string response = GetAPICall("UserImages/Delete/" + Id);
            string message = JsonConvert.DeserializeObject<string>(response);

            TempData["Type"] = "success";
            TempData["Title"] = "Success";
            TempData["Message"] = message;

            return RedirectToAction("Profile", "Home");
        }


        #endregion Profile


        #region Change Password


        public ActionResult ChangePassword()
        {
            if (!HospitalId.HasValue)
                return RedirectToAction("Login", "Home");

            return View();
        }

        [HttpPost]
        public ActionResult ChangePassword(string OldPassword, string NewPassword)
        {
            string response = GetAPICall("Users/ChangePassword?UserId=" + HospitalId + "&oldPassword=" + OldPassword + "&NewPassword=" + NewPassword);
            string message = JsonConvert.DeserializeObject<string>(response);

            if (message == "Password change successfully...!!!")
            {
                TempData["Type"] = "success";
                TempData["Title"] = "Success";
                TempData["Message"] = message;

                return RedirectToAction("Index", "Home");
            }

            TempData["Type"] = "error";
            TempData["Title"] = "Error";
            TempData["Message"] = message;

            return RedirectToAction("ChangePassword", "Home");
        }


        #endregion Change Password


        #region Partial View


        public PartialViewResult ProfileCard(bool IsLink)
        {
            return PartialView("_ProfileCard", IsLink);
        }

        public PartialViewResult DoctorCard(User user, bool IsLink, bool? IsActiveSpecialization)
        {
            string response = GetAPICall("DoctorSpecializations/GetByHospitalDoctorId?HospitalId=" + HospitalId + "&DoctorId=" + user.Id + "&Status=" + IsActiveSpecialization);
            List<Specialization> specializations = JsonConvert.DeserializeObject<List<Specialization>>(response);

            return PartialView("_DoctorCard", (user, IsLink, specializations));
        }


        #endregion Partial View


        public ActionResult Logout()
        {
            Session["HospitalId"] = null;
            Session["FirstName"] = null;
            Session["LastName"] = null;
            Session["Email"] = null;
            Session["MobileNo"] = null;
            Session["ProfileImage"] = null;

            return RedirectToAction("Login", "Home");
        }


        #region Json & Private Methods


        private IEnumerable<SelectListItem> GetCountry()
        {
            string response = GetAPICall("Home/GetCountries");
            List<Country> countries = JsonConvert.DeserializeObject<List<Country>>(response);

            return countries.Select(e => new SelectListItem { Value = e.Id.ToString(), Text = e.Name }).ToList();
        }

        public JsonResult GetStateByCountryId(int CountryId)
        {
            string response = GetAPICall("Home/GetStatesByCountryId?countryId=" + CountryId);
            List<State> states = JsonConvert.DeserializeObject<List<State>>(response);
            List<SelectListItem> items = states.Select(e => new SelectListItem { Value = e.Id.ToString(), Text = e.Name }).ToList();

            return Json(items, JsonRequestBehavior.AllowGet);
        }

        public JsonResult GetCitiesByStateId(int StateId)
        {
            string response = GetAPICall("Home/GetCityByStateId?stateId=" + StateId);
            List<City> cities = JsonConvert.DeserializeObject<List<City>>(response);
            List<SelectListItem> items = cities.Select(e => new SelectListItem { Value = e.Id.ToString(), Text = e.Name }).ToList();

            return Json(items, JsonRequestBehavior.AllowGet);
        }


        #endregion Json & Private Methods
    }
}