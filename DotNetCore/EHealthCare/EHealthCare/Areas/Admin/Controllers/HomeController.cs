using EHealthCare.App_Start;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace EHealthCare.Areas.Admin.Controllers
{
    public class HomeController : BaseController
    {
        // GET: Admin/Home
        public ActionResult Index()
        {
            if (!AdminId.HasValue)
                return RedirectToAction("Login", "Home");

            string response = GetAPICall("Users/GetHospitals");
            List<User> message = JsonConvert.DeserializeObject<List<User>>(response);

            ViewBag.toatalHospital = message.Count();
            ViewBag.totalActiveHospital = message.Where(a => a.IsActive == true).Count();

            response = GetAPICall("Users/GetDoctors");
            message = JsonConvert.DeserializeObject<List<User>>(response);

            ViewBag.totalDoctors = message.Count();
            ViewBag.totalActiveDoctors = message.Where(a => a.IsActive == true).Count();

            response = GetAPICall("Users/GetPatients");
            message = JsonConvert.DeserializeObject<List<User>>(response);

            ViewBag.totalPatients = message.Count();

            return View();
        }

        [HttpGet]
        public ActionResult Login()
        {
            if (AdminId.HasValue)
                return RedirectToAction("Index", "Home");
            return View();
        }

        [HttpPost]
        public ActionResult Login(User user)
        {
            string response = PostAPICall("Users/Login", user);
            (string, User) message = JsonConvert.DeserializeObject<(string, User)>(response);

            if (message.Item1 == "Admin")
            {
                Session["AdminId"] = message.Item2.Id;
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

        public ActionResult Logout()
        {
            Session["AdminId"] = null;
            Session["FirstName"] = null;
            Session["LastName"] = null;
            Session["Email"] = null;
            Session["MobileNo"] = null;
            Session["ProfileImage"] = null;

            return RedirectToAction("Login", "Home");
        }

        public ActionResult ForgetPassword()
        {
            return View();
        }

        [HttpGet]
        public ActionResult Profile()
        {
            if (!AdminId.HasValue)
                return RedirectToAction("Login", "Home");
            return View();
        }

        [HttpGet]
        public ActionResult PutProfileImage()
        {
            if (!AdminId.HasValue)
                return RedirectToAction("Login", "Home");
            return View();
        }

        [HttpPost]
        public ActionResult PutProfileImage(HttpPostedFileBase ProfileImage)
        {
            string response = PostAPIImage("UserImages/PostProfileImage?UserId=" + AdminId, ProfileImage);

            (string, string) message = JsonConvert.DeserializeObject<(string, string)>(response);

            Session["ProfileImage"] = message.Item2;

            TempData["Type"] = "success";
            TempData["Title"] = "Success";
            TempData["Message"] = message.Item1;

            return RedirectToAction("Profile", "Home");

        }

        #region Change Password


        public ActionResult ChangePassword()
        {
            if (!AdminId.HasValue)
                return RedirectToAction("Login", "Home");

            return View();
        }

        [HttpPost]
        public ActionResult ChangePassword(string OldPassword, string NewPassword)
        {
            string response = GetAPICall("Users/ChangePassword?UserId=" + AdminId + "&oldPassword=" + OldPassword + "&NewPassword=" + NewPassword);
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
    }
}