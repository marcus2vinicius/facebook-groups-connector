package view;

import domain.Group;
import domain.User;
import facebook.ConfigurationGroup;
import facebook4j.Facebook;
import facebook4j.Post;
import org.primefaces.model.chart.*;
import process.PostProcess;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by marcus on 01/09/2015.
 */
@ManagedBean
@SessionScoped
public class ChartLineView implements Serializable {
    @ManagedProperty(value = "#{connectView.user}")
    private User user;
    @ManagedProperty(value = "#{homeView.groups}")
    private List<Group> groups;
    private List<facebook4j.Group> faceGroupsList;
    @ManagedProperty(value = "#{connectView.facebook}")
    private facebook4j.Facebook facebook;
    private LineChartModel lineModel;
    private List<facebook4j.Post> temp;

    public ChartLineView(){

    }

    @PostConstruct
    public void init(){
        lineModel = initCategoryModel();
        lineModel.setTitle("Groups Chart");
        lineModel.setLegendPosition("e");
        lineModel.setShowPointLabels(true);
        lineModel.getAxes().put(AxisType.X, new CategoryAxis("Months"));
        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setLabel("Posts");
        yAxis.setMin(0);
    }

    private LineChartModel initCategoryModel(){
        LineChartModel model = new LineChartModel();
        DateFormat f = new SimpleDateFormat("MM/dd/yyyy");
        for(Group g : groups) {
            Date startDateGroup = new Date(2015-1900,0,1);//new Date(new Date().getYear(),g.getStarted().getMonth(),g.getStarted().getDate());
            Date limitDate = startDateGroup;
            limitDate.setDate(new Date().getMonth());
            limitDate.getTime();
            ChartSeries serie = new ChartSeries();
            serie.setLabel(g.getName());
            for (int n = 0; n < 11; n++) {
                startDateGroup.setMonth(n);
                startDateGroup.getTime();
                if(startDateGroup.getMonth() <= limitDate.getMonth())
                    serie.set(theMonth(n), getPostMonth(startDateGroup, g.getGroupId()));
            }
            temp = null;
            model.addSeries(serie);
        }
        return model;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public Integer getPostMonth(Date startDate, String groupId) {
        try {
            return searchGroups(startDate,groupId).size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Facebook getFacebook() {
        return facebook;
    }

    public void setFacebook(Facebook facebook) {
        this.facebook = facebook;
    }



    public List<facebook4j.Post> searchGroups(Date create,String groupId) throws Exception {
        ConfigurationGroup cg = new ConfigurationGroup()
                .setAccessToken(facebook.getOAuthAccessToken().getToken())
                .setFacebook(facebook)
                .setIntervalPoll(1)
                .setLastUpdate(create)
                .setGroupID(groupId)
                .setXapiAccount(null);
        PostProcess p = new PostProcess(cg);
        Calendar untilDate = Calendar.getInstance();
        //untilDate.set(Calendar.MONTH, 12);
        if(temp==null) {
            untilDate.setTime(create);
            untilDate.set(Calendar.MONTH, new Date().getMonth());
            untilDate.set(Calendar.DAY_OF_MONTH, untilDate.getActualMaximum(Calendar.DAY_OF_MONTH));
            temp = p.getPostByCreated(create, untilDate.getTime());
        }
        untilDate.setTime(create);
        untilDate.set(Calendar.DAY_OF_MONTH, untilDate.getActualMaximum(Calendar.DAY_OF_MONTH));
        untilDate.getTime();
        List<Post> list = temp.stream()
                .filter(f -> f.getCreatedTime().getTime() >= create.getTime() &&
                                f.getCreatedTime().getTime() <= untilDate.getTime().getTime()
                ).collect(Collectors.toList());
        return list;
    }

    public LineChartModel getLineModel() {
        return lineModel;
    }

    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }

    private String theMonth(int month){
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month];
    }
}
