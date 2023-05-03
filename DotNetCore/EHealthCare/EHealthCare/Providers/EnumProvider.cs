using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace EHealthCare.Providers
{
    public class EnumProvider
    {
        public enum UserType
        {
            Admin = 1,
            Hospital,
            Doctor,
            Patient
        }
    }
}