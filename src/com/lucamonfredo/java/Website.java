package com.lucamonfredo.java;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Website {
  private SimpleStringProperty siteURL;
  private SimpleIntegerProperty alexaRank;
  private SimpleDoubleProperty visibility;
  private SimpleDoubleProperty transferRateKbs;
  private SimpleDoubleProperty speed;
  private SimpleDoubleProperty navigability;
  private SimpleDoubleProperty content;
  private SimpleDoubleProperty score;

  public Website(String siteURL, int alexaRank, double visibility, double transferRateKbs, double speed, double navigability, double content, double score) {
    this.siteURL = new SimpleStringProperty(siteURL);
    this.alexaRank = new SimpleIntegerProperty(alexaRank);
    this.visibility = new SimpleDoubleProperty(visibility);
    this.transferRateKbs = new SimpleDoubleProperty(transferRateKbs);
    this.speed = new SimpleDoubleProperty(speed);
    this.navigability = new SimpleDoubleProperty(navigability);
    this.content = new SimpleDoubleProperty(content);
    this.score = new SimpleDoubleProperty(score);
  }

  public final String getSiteURL() { return siteURL.get(); }
  public final void setSiteURL(String siteURL) { this.siteURL.set(siteURL); }
  public StringProperty siteURLProperty() { return siteURL; }

  public final int getAlexaRank() { return alexaRank.get(); }
  public final void setAlexaRank(int alexaRank) { this.alexaRank.set(alexaRank); }
  public IntegerProperty alexaRankProperty() { return alexaRank; }

  public final double getVisibility() { return visibility.get(); }
  public final void setVisibility(double visibility) { this.visibility.set(visibility); }
  public DoubleProperty visibilityProperty() { return visibility; }

  public final double getTransferRateKbs() { return transferRateKbs.get(); };
  public final void setTransferRateKbs(double transferRateKbs) { this.transferRateKbs.set(transferRateKbs); }
  public DoubleProperty transferRateKbsProperty() { return transferRateKbs; }

  public final double getSpeed() { return speed.get(); };
  public final void setSpeed(double speed) { this.speed.set(speed); }
  public DoubleProperty speedProperty() { return speed; }

  public final double getNavigability() { return navigability.get(); }
  public final void setNavigability(double navigability) { this.navigability.set(navigability); }
  public DoubleProperty navigabilityProperty() { return navigability; }

  public final double getContent() { return content.get(); }
  public final void setContent(double content) { this.content.set(content); }
  public DoubleProperty contentProperty() { return content; }

  public final double getScore() { return score.get(); }
  public final void setScore(double score) { this.score.set(score); }
  public DoubleProperty scoreProperty() { return score; }
}
