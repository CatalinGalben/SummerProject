import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewMetricsComponent } from './view-metrics.component';

describe('ViewMetricsComponent', () => {
  let component: ViewMetricsComponent;
  let fixture: ComponentFixture<ViewMetricsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewMetricsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewMetricsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
