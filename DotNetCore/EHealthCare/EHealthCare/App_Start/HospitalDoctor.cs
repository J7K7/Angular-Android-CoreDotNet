//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated from a template.
//
//     Manual changes to this file may cause unexpected behavior in your application.
//     Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace EHealthCare.App_Start
{
    using System;
    using System.Collections.Generic;
    
    public partial class HospitalDoctor
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public HospitalDoctor()
        {
            this.Appointments = new HashSet<Appointment>();
            this.DoctorSchedules = new HashSet<DoctorSchedule>();
        }
    
        public int Id { get; set; }
        public int DoctorSpecializationId { get; set; }
        public int HospitalId { get; set; }
        public double Fees { get; set; }
        public bool IsActive { get; set; }
    
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<Appointment> Appointments { get; set; }
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<DoctorSchedule> DoctorSchedules { get; set; }
        public virtual DoctorSpecialization DoctorSpecialization { get; set; }
        public virtual User User { get; set; }
    }
}