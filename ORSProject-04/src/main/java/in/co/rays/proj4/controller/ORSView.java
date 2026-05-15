package in.co.rays.proj4.controller;

public interface ORSView {

	public String  APP_CONTEXT= "/ORSProject-04";

	public String PAGE_FOLDER = "/jsp";
 	
	public String WELCOME_VIEW = PAGE_FOLDER + "/WelcomeView.jsp";
	public String WELCOME_CTL = APP_CONTEXT + "/WelcomeCtl";
	
	public String LOGIN_VIEW = PAGE_FOLDER + "/LoginView.jsp";
	public String LOGIN_CTL = APP_CONTEXT + "/LoginCtl";
	
	public String MY_PROFILE_VIEW = PAGE_FOLDER + "/MyProfileView.jsp";
	public String MY_PROFILE_CTL = APP_CONTEXT + "/ctl/MyProfileCtl";

	public String CHANGE_PASSWORD_VIEW = PAGE_FOLDER + "/ChangePasswordView.jsp";
	public String CHANGE_PASSWORD_CTL = APP_CONTEXT + "/ctl/ChangePasswordCtl";
	
	public String FORGET_PASSWORD_VIEW = PAGE_FOLDER + "/ForgetPasswordView.jsp";
	public String FORGET_PASSWORD_CTL = APP_CONTEXT + "/ForgetPasswordCtl";

	public String GET_MARKSHEET_VIEW = PAGE_FOLDER + "/GetMarksheetView.jsp";
	public String GET_MARKSHEET_CTL = APP_CONTEXT + "/ctl/GetMarksheetCtl";

	public String MARKSHEET_MERIT_LIST_VIEW = PAGE_FOLDER + "/MarksheetMeritListView.jsp";
	public String MARKSHEET_MERIT_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetMeritListCtl";
	
	public String USER_REGISTRATION_VIEW = PAGE_FOLDER + "/UserRegistrationView.jsp";
	public String USER_REGISTRATION_CTL = APP_CONTEXT + "/UserRegistrationCtl";
	
	public String USER_VIEW = PAGE_FOLDER + "/UserView.jsp";
	public String USER_CTL = APP_CONTEXT + "/ctl/UserCtl";

	public String USER_LIST_VIEW = PAGE_FOLDER + "/UserListView.jsp";
	public String USER_LIST_CTL = APP_CONTEXT + "/ctl/UserListCtl";

	public String ROLE_VIEW = PAGE_FOLDER + "/RoleView.jsp";
	public String ROLE_CTL = APP_CONTEXT + "/ctl/RoleCtl";

	public String ROLE_LIST_VIEW = PAGE_FOLDER + "/RoleListView.jsp";
	public String ROLE_LIST_CTL = APP_CONTEXT + "/ctl/RoleListCtl";
	
	public String COLLEGE_VIEW = PAGE_FOLDER + "/CollegeView.jsp";
	public String COLLEGE_CTL = APP_CONTEXT + "/ctl/CollegeCtl";
	
	public String COLLEGE_LIST_VIEW = PAGE_FOLDER + "/CollegeListView.jsp";
	public String COLLEGE_LIST_CTL = APP_CONTEXT + "/ctl/CollegeListCtl";
	
	public String STUDENT_VIEW = PAGE_FOLDER + "/StudentView.jsp";
	public String STUDENT_CTL = APP_CONTEXT + "/ctl/StudentCtl";
	
	public String STUDENT_LIST_VIEW = PAGE_FOLDER + "/StudentListView.jsp";
	public String STUDENT_LIST_CTL = APP_CONTEXT + "/ctl/StudentListCtl";
	
	public String MARKSHEET_VIEW = PAGE_FOLDER + "/MarksheetView.jsp";
	public String MARKSHEET_CTL = APP_CONTEXT + "/ctl/MarksheetCtl";

	public String MARKSHEET_LIST_VIEW = PAGE_FOLDER + "/MarksheetListView.jsp";
	public String MARKSHEET_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetListCtl";
	
	public String TIMETABLE_VIEW = PAGE_FOLDER + "/TimetableView.jsp";
	public String TIMETABLE_CTL = APP_CONTEXT + "/ctl/TimetableCtl";

	public String TIMETABLE_LIST_VIEW = PAGE_FOLDER + "/TimetableListView.jsp";
	public String TIMETABLE_LIST_CTL = APP_CONTEXT + "/ctl/TimetableListCtl";
	
	public String COURSE_VIEW = PAGE_FOLDER + "/CourseView.jsp";
	public String COURSE_CTL = APP_CONTEXT + "/ctl/CourseCtl";
	
	public String COURSE_LIST_VIEW= PAGE_FOLDER + "/CourseListView.jsp";
	public String COURSE_LIST_CTL = APP_CONTEXT + "/ctl/CourseListCtl";

	public String SUBJECT_VIEW = PAGE_FOLDER + "/SubjectView.jsp";
	public String SUBJECT_CTL = APP_CONTEXT + "/ctl/SubjectCtl";
	
	public String SUBJECT_LIST_VIEW = PAGE_FOLDER + "/SubjectListView.jsp";
	public String SUBJECT_LIST_CTL = APP_CONTEXT + "/ctl/SubjectListCtl";

	public String FACULTY_VIEW = PAGE_FOLDER + "/FacultyView.jsp";
	public String FACULTY_CTL = APP_CONTEXT + "/ctl/FacultyCtl";
	
	public String FACULTY_LIST_VIEW = PAGE_FOLDER + "/FacultyListView.jsp";
	public String FACULTY_LIST_CTL = APP_CONTEXT + "/ctl/FacultyListCtl";
	
	public String EMI_VIEW = PAGE_FOLDER + "/EmiView.jsp";
	public String EMI_CTL = APP_CONTEXT + "/ctl/EmiCtl";
	
	public String EMI_LIST_VIEW= PAGE_FOLDER + "/EmiListView.jsp";
	public String EMI_LIST_CTL = APP_CONTEXT + "/ctl/EmiListCtl";
	
	public String PASSWORD_VIEW = PAGE_FOLDER + "/PasswordView.jsp";
	public String PASSWORD_CTL = APP_CONTEXT + "/ctl/PasswordCtl";
	
	public String PASSWORD_LIST_VIEW= PAGE_FOLDER + "/PasswordListView.jsp";
	public String PASSWORD_LIST_CTL = APP_CONTEXT + "/ctl/PasswordListCtl";
	
	public String WARRANTY_VIEW = PAGE_FOLDER + "/WarrantyView.jsp";
	public String WARRANTY_CTL = APP_CONTEXT + "/ctl/WarrantyCtl";
	
	public String WARRANTY_LIST_VIEW= PAGE_FOLDER + "/WarrantyListView.jsp";
	public String WARRANTY_LIST_CTL = APP_CONTEXT + "/ctl/WarrantyListCtl";
	
	public String TOPIC_VIEW = PAGE_FOLDER + "/TopicView.jsp";
	public String TOPIC_CTL = APP_CONTEXT + "/ctl/TopicCtl";

	public String TOPIC_LIST_CTL = APP_CONTEXT + "/ctl/TopicListCtl";
	public String TOPIC_LIST_VIEW = PAGE_FOLDER + "/TopicListView.jsp";
	
	public String BATCH_VIEW = PAGE_FOLDER + "/BatchView.jsp";
	public String BATCH_CTL = APP_CONTEXT + "/ctl/BatchCtl";

	public String BATCH_LIST_CTL = APP_CONTEXT + "/ctl/BatchListCtl";
	public String BATCH_LIST_VIEW = PAGE_FOLDER + "/BatchListView.jsp";
	
	public String JOB_VIEW = PAGE_FOLDER + "/JobView.jsp";
	public String JOB_CTL = APP_CONTEXT + "/ctl/JobCtl";

	public String JOB_LIST_CTL = APP_CONTEXT + "/ctl/JobListCtl";
	public String JOB_LIST_VIEW = PAGE_FOLDER + "/JobListView.jsp";

	public String SOURCE_VIEW = PAGE_FOLDER + "/SourceView.jsp";
	public String SOURCE_CTL = APP_CONTEXT + "/ctl/SourceCtl";

	public String SOURCE_LIST_CTL = APP_CONTEXT + "/ctl/SourceListCtl";
	public String SOURCE_LIST_VIEW = PAGE_FOLDER + "/SourceListView.jsp";
	
	public String SESSION_VIEW = PAGE_FOLDER + "/SessionView.jsp";
	public String SESSION_CTL = APP_CONTEXT + "/ctl/SessionCtl";

	public String SESSION_LIST_CTL = APP_CONTEXT + "/ctl/SessionListCtl";
	public String SESSION_LIST_VIEW = PAGE_FOLDER + "/SessionListView.jsp";
	
	public String COMMAND_VIEW = PAGE_FOLDER + "/CommandView.jsp";
	public String COMMAND_CTL = APP_CONTEXT + "/ctl/CommandCtl";

	public String COMMAND_LIST_CTL = APP_CONTEXT + "/ctl/CommandListCtl";
	public String COMMAND_LIST_VIEW = PAGE_FOLDER + "/CommandListView.jsp";
	
	public String GENDER_VIEW = PAGE_FOLDER + "/GenderView.jsp";
	public String GENDER_CTL = APP_CONTEXT + "/ctl/GenderCtl";

	public String GENDER_LIST_CTL = APP_CONTEXT + "/ctl/GenderListCtl";
	public String GENDER_LIST_VIEW = PAGE_FOLDER + "/GenderListView.jsp";
	
	public String DISASTER_VIEW = PAGE_FOLDER + "/DisasterView.jsp";
	public String DISASTER_CTL = APP_CONTEXT + "/ctl/DisasterCtl";

	public String DISASTER_LIST_CTL = APP_CONTEXT + "/ctl/DisasterListCtl";
	public String DISASTER_LIST_VIEW = PAGE_FOLDER + "/DisasterListView.jsp";
	
	public String ENERGY_VIEW = PAGE_FOLDER + "/EnergyView.jsp";
	public String ENERGY_CTL = APP_CONTEXT + "/ctl/EnergyCtl";

	public String ENERGY_LIST_CTL = APP_CONTEXT + "/ctl/EnergyListCtl";
	public String ENERGY_LIST_VIEW = PAGE_FOLDER + "/EnergyListView.jsp";
	
	
	public String EMOJI_VIEW = PAGE_FOLDER + "/EmojiView.jsp";
	public String EMOJI_CTL = APP_CONTEXT + "/ctl/EmojiCtl";

	public String EMOJI_LIST_CTL = APP_CONTEXT + "/ctl/EmojiListCtl";
	public String EMOJI_LIST_VIEW = PAGE_FOLDER + "/EmojiListView.jsp";
	
	


	public String ERROR_VIEW = PAGE_FOLDER + "/ErrorView.jsp";
	public String ERROR_CTL = APP_CONTEXT + "/ctl/ErrorCtl";
	
	public String JAVA_DOC = "/ORSProject-04/doc/index.html";

}