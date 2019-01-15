package com.io.insurance.ClientData.service;

import com.io.insurance.ClientData.models.DamageReport;
import com.io.insurance.ClientData.models.Insurance;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DamageReportServiceTest {

    private static DamageReportService mockedService;
    private static DamageReport report1;
    private static DamageReport report2;

    @BeforeEach
    void setUp() {
        mockedService = mock(DamageReportService.class);

        Map<String,String> data1 = new HashMap<>();
        data1.put("data1", "1");
        Map<String,String> data2 = new HashMap<>();
        data2.put("data2", "2");

        Insurance.InsuranceType type1 = Insurance.InsuranceType.HOME;
        Insurance.InsuranceType type2 = Insurance.InsuranceType.VEHICLE;

        report1 = new DamageReport("1", type1, data1);
        report2 = new DamageReport("2", type2, data2);

        when(mockedService.findById("1")).thenReturn(report1);
        when(mockedService.findById("2")).thenReturn(report2);

        when(mockedService.save(report1)).thenReturn("1");
        when(mockedService.save(report2)).thenReturn("2");

        when(mockedService.update(report1)).thenReturn("1");
        when(mockedService.update(report2)).thenReturn("2");

        when(mockedService.delete(report1)).thenReturn(true);
        when(mockedService.delete(report2)).thenReturn(true);

        when(mockedService.findAllByClientId("1")).thenReturn(Arrays.asList(report1));
        when(mockedService.findAllByClientId("2")).thenReturn(Arrays.asList(report2));

        when(mockedService.findAllByClientIdAndType("1", Insurance.InsuranceType.HOME)).thenReturn(Arrays.asList(report1));
        when(mockedService.findAllByClientIdAndType("2", Insurance.InsuranceType.VEHICLE)).thenReturn(Arrays.asList(report2));
    }

    @Test
    void findById1() {
        DamageReport report = mockedService.findById("1");
        Assert.assertEquals(report, report1);
    }

    @Test
    void findByID2() {
        DamageReport report = mockedService.findById("2");
        Assert.assertEquals(report, report2);
    }

    @Test
    void save1() {
        String reportId = mockedService.save(report1);
        Assert.assertEquals(reportId, "1");
    }

    @Test
    void save2() {
        String reportId = mockedService.save(report2);
        Assert.assertEquals(reportId, "2");
    }

    @Test
    void update1() {
        String reportId = mockedService.update(report1);
        Assert.assertEquals(reportId, "1");
    }

    @Test
    void update2() {
        String reportId = mockedService.update(report2);
        Assert.assertEquals(reportId, "2");
    }

    @Test
    void delete1() {
        Boolean reportBoolean = mockedService.delete(report1);
        Assert.assertEquals(reportBoolean, true);
    }

    @Test
    void delete2() {
        Boolean reportBoolean = mockedService.delete(report2);
        Assert.assertEquals(reportBoolean, true);
    }

    @Test
    void findAllByClientId1() {
        List<DamageReport> reports = mockedService.findAllByClientId("1");
        Assert.assertEquals(reports, Arrays.asList(report1));
    }

    @Test
    void findAllByClientId2() {
        List<DamageReport> reports = mockedService.findAllByClientId("2");
        Assert.assertEquals(reports, Arrays.asList(report2));
    }

    @Test
    void findAllByClientIdAndType1() {
        List<DamageReport> reports = mockedService.findAllByClientIdAndType("1", Insurance.InsuranceType.HOME);
        Assert.assertEquals(reports, Arrays.asList(report1));
    }

    @Test
    void findAllByClientIdAndType2() {
        List<DamageReport> reports = mockedService.findAllByClientIdAndType("2", Insurance.InsuranceType.VEHICLE);
        Assert.assertEquals(reports, Arrays.asList(report2));
    }
}