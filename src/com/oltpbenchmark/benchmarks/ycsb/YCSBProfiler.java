package com.oltpbenchmark.benchmarks.ycsb;

import java.lang.*;
import java.util.*;
import java.time.*;

public class YCSBProfiler {


  // private class ProfileEntry {
  //   public ProfileEntry() {}

  //   public String name;
  //   public long time;
  // }

  // private static List<ProfileEntry> entries = new ArrayList<ProfileEntry>();
  private static String[] profile_names = new String[100];
  private static Long[] profile_times = new Long[100];

  private static int entry_count = 0;
  private static int txn_count = 0;
  private static long begin_time = 0;
  
  public static void BeginTransaction() {
    txn_count++;
    if (txn_count % 2000 == 0) {
      // entries.clear();
      entry_count = 0;

      Instant inst = Instant.now();
      begin_time = inst.getEpochSecond();
      begin_time *= 1000000000l; //convert to nanoseconds
      begin_time += inst.getNano();

      // begin_time = System.nanoTime();
    }
  }

  public static void InsertTimePoint(String point_name) {
    if (txn_count % 2000 == 0) {
      profile_names[entry_count] = point_name;

      Instant inst = Instant.now();
      profile_times[entry_count] = inst.getEpochSecond();
      profile_times[entry_count] *= 1000000000l; //convert to nanoseconds
      profile_times[entry_count] += inst.getNano();

      // profile_times[entry_count] = System.nanoTime();
      entry_count++;

      // entries.add(new ProfileEntry(point_name, System.nanoTime()));
    }
  }

  public static void EndTransaction() {

    if (txn_count % 2000 == 0) {
      // long end_time = System.nanoTime();

      Instant inst = Instant.now();
      long end_time = inst.getEpochSecond();
      end_time *= 1000000000l; //convert to nanoseconds
      end_time += inst.getNano();

      System.out.println("========================");
      System.out.println("txn count = " + txn_count);

      System.out.println("begin clock: " + begin_time);
      for (int i = 0; i < entry_count; ++i) {
        System.out.println("point: " + profile_names[i] + ", time: " + (profile_times[i] - begin_time) / 1000.0 + " us, clock: " + profile_times[i]);
      }
      System.out.println("point: end, time: " + (end_time - begin_time) / 1000 + " us, clock: " + end_time);
      

    }
  }
}
