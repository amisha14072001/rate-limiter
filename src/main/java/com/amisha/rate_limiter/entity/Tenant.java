package com.amisha.ratelimiter.entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
@Entity
@Table(name = "tenants")
@Data                   // lombok: generates getters, setters, toString
@NoArgsConstructor      // lombok: generates empty constructor
@AllArgsConstructor     // lombok: generates full constructor
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // ↑ auto-increment: 1, 2, 3... Hibernate handles this

    @Column(unique= true ,nullable=false)
    private String tenantKey;

       // ↑ this is what goes inside the JWT token
    // e.g. "walmart-001", "openai-free-tier"
    // unique = no two tenants can have same key
    // nullable = false means this field is required

    @Column(nullable = false)
    private String tenantName;
    // ↑ human readable name, just for display
    // e.g. "Walmart Inc", "OpenAI Free User"

    @Column(nullable = false)
    private Integer limitValue;
    // ↑ how many requests allowed per window
    // FREE = 100, PRO = 1000, ENTERPRISE = 10000

    @Column(nullable = false)
    private Integer windowSeconds;
    // ↑ the time window size in seconds
    // e.g. 60 = per minute, 3600 = per hour

    @Column(nullable = false)
    private String plan;
    // ↑ FREE / PRO / ENTERPRISE
    // controls which features they can access

    @Column(nullable = false)
    private String unitType;
    // ↑ requests / tokens / cost
    // this is the pluggable unit we designed earlier
    // default = "requests" for now

    @Column(nullable = false)
    private Boolean active;
    // ↑ true = tenant can make requests
    // false = tenant is blocked (without deleting their record)

    @Column(updatable = false)
    private LocalDateTime createdAt;
    // ↑ when was this tenant created
    // updatable = false means once set, never changes

    @PrePersist
    // ↑ this method runs automatically BEFORE saving to database
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

}